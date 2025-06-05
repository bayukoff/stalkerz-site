import Main from "./Main";
import React from "react";
import SideComponent from "../components/SideComponent";
import RegistrationComponent from "../components/RegistrationComponent";

function Registration() {
    return (
        <div className="main__wrapper">
            <RegistrationComponent/>
            <SideComponent/>
        </div>
    );
}

export default Registration;