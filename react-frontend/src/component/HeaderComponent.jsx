import React from 'react'
import { useContext } from 'react';
import { AppContext } from '../context/AppContext'; // Importing AppContext to access userData


const HeaderComponent = () => {
  const { userData } = useContext(AppContext); // Importing userData from AppContext

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
      <button className="btn btn-outline-dark rounded-pill px-4 py-2">
        Get Started 
      </button>   
    </div>
   
  )
}

export default HeaderComponent
