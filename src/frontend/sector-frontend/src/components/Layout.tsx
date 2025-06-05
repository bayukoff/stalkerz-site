import React from "react";
import HeaderComponent from "./HeaderComponent";
import {Outlet} from "react-router-dom";
import SideComponent from "./SideComponent";

const Layout = () => {
    return (
        <>
            <HeaderComponent/>
            <Outlet/>
        </>
    )
}

export default Layout