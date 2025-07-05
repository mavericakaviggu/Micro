import {createContext, useState } from 'react';
import AppConstants from '../util/constants';


export const AppContext = createContext();

export const AppContextProvider = (props) => {

    const backendURL = AppConstants.BACKEND_BASE_URL;
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userData, setUserData] = useState(null);

    const contextValue={
        backendURL: backendURL,
        isLoggedIn: isLoggedIn,
        setIsLoggedIn: setIsLoggedIn,
        userData: userData,
        setUserData: setUserData,
        // Add other context values here as needed
    }

    return(
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    )
}
