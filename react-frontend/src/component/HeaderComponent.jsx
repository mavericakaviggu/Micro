import React, { useContext, useState } from 'react';
import { AppContext } from '../context/AppContext'; // Importing AppContext to access userData
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const HeaderComponent = () => {
  const { userData, isLoggedIn } = useContext(AppContext); // Importing userData from AppContext
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  // to check if the user is logged in and redirect accordingly
  // If logged in, redirect to employee management, otherwise redirect to login page
  const handleGetStarted = () => {
    setLoading(true);
    toast.info(isLoggedIn? "Redirecting to employee management..." : "Please login to continue");

    setTimeout(() => {
      if(isLoggedIn) {
      navigate('/employees');
      } else {
      navigate('/login');
      }
      setLoading(false);
    }, 1000); // Simulate a delay for the toast notification
    };

  return (
    <div className="d-flex flex-column align-items-center justify-content-center text-center vh-100 py-5 px-3">
      {/* <img src={assets.header} alt="header" width={120} className='mb-3' /> */}
      <h5 className="fw-semibold">
        Hey {userData? userData.name : 'Developer'} <span role="img" aria-label="wave">👋</span> 
      </h5>
      <br/>
      <h1 className="fw-bold display-5 mb-3">Welcome to our Product </h1>
      <br/>
      <p className="text-muted fs-5 mb-4" style={{maxWidth: "500px"}}>
        Lets start with a quick product tour and setup your account.
      </p>   
      <br/>
      <button className="btn btn-outline-dark rounded-pill px-4 py-2" onClick={handleGetStarted} disabled={loading}>
        {loading ? "Loading..." : isLoggedIn ? "Get Started" : "Login to Get Started"}
      </button>   
    </div>
  )
}

export default HeaderComponent
