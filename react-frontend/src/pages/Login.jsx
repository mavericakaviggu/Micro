import { Link } from "react-router-dom";
import { assets } from "../assets/assets";
import React, { useState } from "react";

const Login = () => {
    const [isCreateAccount, setIsCreateAccount] = useState(true); //to toggle between login and create account forms
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
                <form>
                    {isCreateAccount && (
                        <div className="px-4">
                            <div className="mb-3">
                                <label htmlFor="name" className="form-label">Name</label>
                                <input type="text" className="form-control" id="name" placeholder="Enter your name" required />
                            </div>
                        </div>
                    )}
                    <div className="px-4">
                        <div className="mb-3">
                            <label htmlFor="email" className="form-label">Email</label>
                            <input type="email" className="form-control" id="email" placeholder="Enter your email" required />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" placeholder="Enter your password" required />
                        </div>
                        <div className="d-flex justify-content-between mb-3">
                            <Link to="/reset-password" className="text-decoration-none">Forgot Password?</Link>
                        </div>
                        <button type="submit" className="btn btn-primary w-100 mb-2">
                            {isCreateAccount ? "Sign Up" : "Login"}
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