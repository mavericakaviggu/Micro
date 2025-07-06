import { Link } from "react-router-dom";
import { assets } from "../assets/assets";
import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { AppContext} from "../context/AppContext"; //importing AppContext and userContext
import { toast } from "react-toastify"; //importing toast for notifications


const Login = () => {
    const [isCreateAccount, setIsCreateAccount] = useState(false); //to toggle between login and create account forms
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const {backendURL, setIsLoggedIn, getUserData} = useContext(AppContext);  //importing backend URL from context


    const onSubmitHandler = async (e) => {
        e.preventDefault();
        axios.default.withCredentials = true; //to allow cookies to be sent with the request
        setLoading(true);
        try{
            if(isCreateAccount){
                //register user
                console.log("Backend URL:", backendURL);
                const response = await axios.post(`${backendURL}/register`, {name,email,password})
                if(response.status === 201){
                    //user registered successfully
                    navigate("/");
                    toast.success("Account created successfully!");
                    setIsCreateAccount(false); //switch to login form
                }else{
                    //handle error
                    toast.error("Email already exists.Error creating account. Please try again.");
                }

            }else{
                //login user
                const response = await axios.post(`${backendURL}/login`, {email, password});
                if(response.status === 200){
                    const token = response.data.token; //get token from response
                    localStorage.setItem("token", token); //store token in local storage
                    //user logged in successfully
                    setIsLoggedIn(true); //set logged in state to true
                    toast.success("Login successful!");
                    await getUserData(); //fetch user data after login
                    navigate("/");
                    //setUserData(userData); //uncomment if you have setUserData in context
            }else{
                    //handle error
                    toast.error("Invalid email or password. Please try again.");
                }
            }
        }catch(error){
            toast.error(error.response?.data?.message || "An error occurred. Please try again.");
        }finally{
            setLoading(false); //reset loading state
        }
    }

    return (
        <div className="position-relative min-vh-100 d-flex justify-content-center align-items-center" style={{ backgroundImage: "linear-gradient(90deg, #6a5af9, #8268f9)", border: "none" }}>
            <div style={{position: "absolute", top: "20px", left: "30px", display:"flex", alignItems:"center"}}>
                <Link to="/" style={{display:"flex", alignItems: "center", textDecoration: "none", color: "white", fontWeight: "bold", fontSize: "24px", gap: 5}}>
                    <img src={assets.logo} alt="logo" height={32} width={32}/>
                    <span className="fw-bold fs-4 text-white">Authify</span> 
                </Link>
            </div>
            <div className="card" style={{maxWidth:"400px", width:"100%"}}>
                <h2 className="text-center my-4">
                    {isCreateAccount ? "Create Account": "Login"}
                </h2>
                <form onSubmit={onSubmitHandler}>
                    {isCreateAccount && (
                        <div className="px-4">
                            <div className="mb-3">
                                <label htmlFor="name" className="form-label">Name</label>
                                <input type="text" className="form-control" id="name" placeholder="Enter your name" required 
                                value={name} onChange={(e)=> setName(e.target.value)}/>
                            </div>
                        </div>
                    )}
                    <div className="px-4">
                        <div className="mb-3">
                            <label htmlFor="email" className="form-label">Email</label>
                            <input type="email" className="form-control" id="email" placeholder="Enter your email" required 
                            value={email} onChange={(e)=> setEmail(e.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" placeholder="Enter your password" required 
                            value={password} onChange={(e)=>setPassword(e.target.value)}/>
                        </div>
                        <div className="d-flex justify-content-between mb-3">
                            <Link to="/reset-password" className="text-decoration-none">Forgot Password?</Link>
                        </div>
                        <button type="submit" className="btn btn-primary w-100 mb-2" disabled={loading}>
                            {loading ? "Loading......" : isCreateAccount ? "Sign Up" : "Login"}
                        </button>  
                    </div>
                </form>
                <div className="text-center mt-2">
                    <p className="mb-2">
                        {isCreateAccount ? 
                        (
                        <>
                            Already have an account? {" "}
                            <span onClick={()=>setIsCreateAccount(false)} className="text-decoration-underline" style={{cursor:"pointer"}}>Login here</span> 
                        </>
                        ) : (
                        <>
                            Don't have an account?{" "}
                            <span onClick={()=>setIsCreateAccount(true)} className="text-decoration-underline" style={{cursor:"pointer"}}> Sign Up</span>
                        </>
                    )}
                    </p>
                </div>
            </div>
        </div>
    );
}

export default Login;