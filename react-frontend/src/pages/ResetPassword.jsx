import { assets } from "../assets/assets";
import { Link } from "react-router-dom";
import React, { useContext, useEffect, useRef } from "react";
import { AppContext } from "../context/AppContext"; // Importing AppContext to access user data if needed
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify"; // Importing toast for notifications

const ResetPassword = () => {

    const inputRef = useRef([]);
    const [loading, setLoading] = React.useState(false);
    const {getUserData, isLoggedIn, userData, backendURL} = useContext(AppContext); // Importing AppContext to access user data if needed
    const navigate = useNavigate();
    const[email, setEmail] = React.useState("");
    const [newPassword, setNewPassword] = React.useState("");
    const[isEmailSent, setIsEmailSent] = React.useState(false);
    const [otp, setOtp] = React.useState("");
    const [isOtpSubmitted, setIsOtpSubmitted] = React.useState(false);


    axios.defaults.withCredentials = true; // to allow cookies to be sent with the request

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
        setOtp(otp); // Set the OTP state
        setIsOtpSubmitted(true);
    } 

    const onSubmitNewPassword = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const response = await axios.post(backendURL + "/reset-password", {
                email: email,
                otp: otp,
                newPassword: newPassword
            });
            if (response.status === 200) {
                toast.success("Password reset successfully!");
                navigate("/login"); // Redirect to login page after successful password reset
            } else {
                toast.error("Failed to reset password. Please try again.");
            }
        } catch (error) {
            toast.error(error.response?.data?.message || "An error occurred while resetting the password.");
        } finally {
            setLoading(false);
        }
    }


    const onSubmitEmail = async (e) => {
        e.preventDefault();
        setLoading(true);
        try{
            const response = await axios.post(backendURL+"/send-reset-otp?email="+email) 
            if(response.status === 200){
                toast.success("Password resetOTP sent successfully!");
                setIsEmailSent(true);            
            }else{
                toast.error("Failed to send reset password OTP. Please try again.");
            }
        }catch(error){
            toast.error(error.response?.data?.message)
        }finally{
            setLoading(false);
        }
    }

    return (
        <div className="d-flex align-items-center justify-content-center vh-100 position-relative" style={{background: "linear-gradient(90deg, #6a5af9, #8268f9)", "border":"none"}}>
            <Link to="/" className="position-absolute top-0 start-0 p-4 d-flex align-items-center gap-2 text-decoration-none">
                <img src={assets.logo} alt="logo" height={32} width={32}/>
                <span className="fs-4 fw-semibold text-light">Authify</span>
            </Link>

            {/* Reset password card */}
            {!isEmailSent && (
                <div className="rounded-4 p-5 text-center bg-white" style={{width:"100%", maxWidth: "400px"}}>
                    <h4 className="mb-2">Reset Password</h4>
                    <p className="mb-">Enter your registered email address</p>
                    <form onSubmit={onSubmitEmail}>
                        <div className="input-group mb-4 bg-secondary bg-opacity-10 rounded-pill">
                            <span className="input-group-text bg-transparent border-0 ps-4">
                                <i className="bi bi-envelope"></i>

                            </span>
                            <input type="email" className="form-control bg-transparent border-0 ps-1 pe-4 rounded-end" placeholder="Enter email address" style={{height:"50px"}} onChange={(e)=>setEmail(e.target.value)} value={email} required/>
                        </div>
                        <button className="btn btn-primary w-100 py-2" type="submit" disabled={loading}>
                            {loading ? "Loading ..." : "Submit"}
                        </button>
                    </form>
                </div>
            )}
            {/*OTP Card*/}
            { isEmailSent && !isOtpSubmitted && ( 
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
                {/* <p className="text-center fw-3 mb-4">
                    Didn't receive the code? <span className="text-decoration-underline" style={{cursor:"pointer"}}>Resend</span>
                </p> */}
                <button className="btn btn-primary w-100 fw-semibold" disabled={loading} onClick={handleVerify}>
                    {loading ? "Verifying..." : "Verify Email"}
                </button>            
            </div>
            )}  
            {/*New password form*/}
                    {isOtpSubmitted && isEmailSent && (
                        <div className="rounded-4 p-4 text-center bg-white" style={{width:"100%", maxWidth:"400px"}}>
                            <h4 className="text-center mb-4">Set New Password</h4>
                            <p className="mb-4">Enter the new password below</p>
                            <form onSubmit={onSubmitNewPassword}>
                                <div className="input-group mb-4 bg-secondary bg-opacity-10 rounded-pill bg-secondary">
                                    <span className="input-group text bg-transparent border-0 ps-4">
                                        <i className="bi bi-person-fill-lock"></i>

                                    </span>
                                    <input type="password" className="form-control bg-transparent border-0 ps-1 pe-4 rounded-end" placeholder="**********" style={{height:"50px"}} onChange={(e)=>setNewPassword(e.target.value)} value={newPassword} required/>
                                </div>
                                <button type="submit" className="btn btn-primary w-100" disabled={loading}>
                                    {loading ? "Loading ..." : "Submit"}
                                </button>                        
                            </form>
                        </div>
                    )}   
        </div>
    );
}

export default ResetPassword;