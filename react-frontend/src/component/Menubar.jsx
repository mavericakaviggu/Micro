import { useNavigate } from "react-router-dom";
import { assets } from "../assets/assets";
import React, { useContext, useState, useRef, useEffect } from "react";
import { AppContext } from "../context/AppContext"; // Importing AppContext to access user
import axios from "axios";
import { toast } from "react-toastify";


const Menubar = () => {
  const navigate = useNavigate();
  const {userData, backendURL, setUserData, setIsLoggedIn} = useContext(AppContext); // Importing userData from AppContext
  const[dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);
  
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleLogout = async() => {
    try{
      axios.default.withCredentials = true; //to allow cookies to be sent with the request
      const response = await axios.post(`${backendURL}/logout`);
      if(response.status === 200){
        setIsLoggedIn(false); //set logged in state to false
        setUserData(false); //clear user data
        navigate("/login");
      }}
      catch(error){
        toast.error(error.response?.data?.message || "An error occurred while logging out.");
      }}

  return (
    <nav className="navbar fixed-top bg-white px-5 py-4 d-flex justify-between items-center shadow-md">
      <div className="d-flex align-items-center gap-2">
        <img src={assets.logo} alt=" Logo" />
        <span className="fw-bold fs-4 text-dark">Authify</span>
      </div>

      { userData ? (
        <div className="position-relative" ref={dropdownRef}>
          <div className="bg-dark text-white rounded-circle d-flex justify-content-center align-items-center" style={{ width: "40px", height: "40px", cursor: "pointer", userSelect:"none" }} onClick={() => setDropdownOpen(()=> !dropdownOpen)}>
            { userData.name ? userData.name.charAt(0).toUpperCase() : "U" }
          </div>
          {dropdownOpen && (
          <div className="position-absolute rounded bg-white shadow-lg" style={{ top: "50px", right: "0", zIndex: 100 }}>
            {!userData.isAccountVerified && (
            <div className="dropdown-item py-1 px-2" style={{ cursor: "pointer" }} onClick={() => navigate('/email-verify')}>
              Verify email
            </div>
            )}
            <div className="dropdown-item py-1 px-2 text-danger" style={{ cursor: "pointer" }} onClick={handleLogout}>
              Logout 
            </div>
          </div>
          )}
        </div>
      )      
      :
      (
        <div className="btn btn-outline-dark rounded-pill px-3" onClick={() => navigate('/login')}>
          Login <i className="bi bi-arrow-right ms-2"></i>
        </div>
      )} 
    </nav>
  )
}
export default Menubar;
