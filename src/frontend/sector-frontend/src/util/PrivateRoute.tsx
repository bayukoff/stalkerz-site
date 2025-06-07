import {PropsWithChildren, ReactNode, useContext} from "react";
import {AuthContext} from "../context/AuthContext";
import {Navigate} from "react-router-dom";

const PrivateRoute = ({children}: any) => {
    const{wasLoaded, user} = useContext(AuthContext)
    if (!wasLoaded)
        return null
    if (user)
        return children
    else return <Navigate to={"/login"}/>

}

export default PrivateRoute