import React from "react";
import UserComponent from "../components/user-components/UserComponent";
import SideComponent from "../components/SideComponent";

const User = () => {
    return (
        <div className="main__wrapper">
            <UserComponent/>
            <SideComponent/>
        </div>
    )
}

export default User