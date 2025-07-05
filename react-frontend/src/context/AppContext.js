import {createContext, useState } from 'react';
import AppConstants from '../util/constants';
import axios from 'axios';
import { toast } from 'react-toastify';


export const AppContext = createContext();

export const AppContextProvider = (props) => {

    const backendURL = AppConstants.BACKEND_BASE_URL;
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userData, setUserData] = useState(null);

    const getUserData = async() => {
        try{
            const token = localStorage.getItem("token");
            const response = await axios.get(`${backendURL}/profile`,{
                headers: {
                    Authorization: `Bearer ${token}` // Include the token in the request headers
                }
            });
            if(response.status === 200){
                setUserData(response.data);
            }else{
            toast.error("Unable to retrieve data.");
            }
        }catch(error){
            toast.error(error.response?.data?.message || "An error occurred while fetching user data.");
        }
    }
    

    const contextValue={
        backendURL: backendURL,
        isLoggedIn: isLoggedIn,
        setIsLoggedIn: setIsLoggedIn,
        userData: userData,
        getUserData: getUserData,   
        setUserData: setUserData,
        // Add other context values here as needed
    }

    return(
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    )
}
