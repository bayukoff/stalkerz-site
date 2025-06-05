import React from "react";
import HeaderComponent from "../components/HeaderComponent";
import LoginComponent from "../components/LoginComponent";
import SideComponent from "../components/SideComponent";

function Login() {
    return (
        <div className="main__wrapper">
            <LoginComponent/>
            <SideComponent/>
        </div>
    );
}

export default Login;