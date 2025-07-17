import { Link, useNavigate } from "react-router-dom";
import { assets } from "../assets/assets";
import React, { useContext, useEffect, useRef } from "react";
import { AppContext } from "../context/AppContext"; // Importing AppContext to access user data if needed
import axios from "axios";
import { toast } from "react-toastify"; // Importing toast for notifications
  
const EmailVerify = () => {

    const inputRef = useRef([]);
    const [loading, setLoading] = React.useState(false);
    const {getUserData, isLoggedIn, userData, backendURL} = useContext(AppContext); // Importing AppContext to access user data if needed
    const navigate = useNavigate();

    const handleChange = (e, index) => {
        const value = e.target.value.replace(/\D/, ""); // Remove any non-digit characters (eg "a" will become "" and "3a" will become "3")
        e.target.value = value; // Update the input value to only digits validated in the above step
        if (value && index < 5) { //if the input is not empty and not the last input
            inputRef.current[index + 1].focus(); // Move the cursor to the next input box
        }
    }

    const handleKeyDown = (e, index) => {
        if (e.key === "Backspace" && !e.target.value && index > 0) {
            inputRef.current[index - 1].focus(); // Move cursor to the previous input box on backspace
        }
    }

    const handlePaste = (e) =>{
        e.preventDefault(); //Prevents the default paste behavior (which would paste all 6 digits into a single box).
        const paste = e.clipboardData.getData("text").slice(0,6).split("");
        paste.forEach((digit, i) =>{
            if (inputRef.current[i]) {
                inputRef.current[i].value = digit; // Paste each digit into the respective input
            }
        });
        const next = paste.length < 6 ? paste.length : 5; //Calculates which field should be focused after pasting.
        inputRef.current[next].focus(); // Move focus to the next input after pasting
    }

    const handleVerify = async (e) => {
        const otp = inputRef.current.map(input => input.value).join(""); // Get the values from all input fields
        if(otp.length !== 6) {
            toast.error("Please enter a valid 6-digit OTP.");   
            return;
        }
        setLoading(true);
        try {
            const token = localStorage.getItem("token");
            const response = await axios.post(`${backendURL}/verify-otp`, { otp },
            {
                withCredentials: true, // send cookies too if needed
            });
            if (response.status === 200) {
                toast.success("Email verified successfully!");
                await getUserData(); // Fetch user data after verification
                navigate("/"); // Redirect to home page after successful verification
            } else {
                toast.error("Invalid OTP. Please try again.");
            }
        } catch (error) {
            toast.error(error.response?.data?.message || "An error occurred while verifying the email.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        isLoggedIn && userData && userData?.isAccountVerified && navigate("/"); // Redirect to home page if user is already verified his email
        }, [isLoggedIn, userData]);


    return (
        <div className="email-verify-container d-flex align-items-center justify-content-center vh-100 position-relative" style={{background:"linear-gradient(90deg, #6a5af9, #8268f9)"}}>
            <Link to="/" className="position-absolute top-0 start-0 p-4 d-flex align-items-center gap-2 text-decoration-none" style={{zIndex: 10}}>
                <img src={assets.logo} alt="logo" height={32} width={32} />
                <span className="fs-4 fw-semibold text-light">Authify</span>
            </Link>

            <div className="p-5 rounded-4 shadow bg-white" style={{width:"400px"}}>
                <h4 className="text-center fw-bold mb-2">Email Verify OTP</h4>
                <p className="text-center fw-3 mb-4">
                    Enter the 6 digit code sent to your email address to verify your account.
                </p>
                <div className="d-flex justify-content-between gap-2 mb-4 text-center text-white-50 mb-2" onPaste = {handlePaste}>
                    {[...Array(6)].map((_, index) => (
                        <input 
                            key={index}
                            type="text" 
                            maxLength="1" 
                            className="form-control text-center fs-4 otp-input"
                            ref={(el) => (inputRef.current[index] = el)}
                            onChange = {(e) => handleChange(e, index)}
                            onKeyDown = {(e) => handleKeyDown(e, index)}
                        />
                    ))}
                </div>
                <p className="text-center fw-3 mb-4">
                    Didn't receive the code? <span className="text-decoration-underline" style={{cursor:"pointer"}}>Resend</span>
                </p>
                <button className="btn btn-primary w-100 fw-semibold" disabled={loading} onClick={handleVerify}>
                    {loading ? "Verifying..." : "Verify Email"}
                </button>            
            </div>
        </div>
    )
}

export default EmailVerify;