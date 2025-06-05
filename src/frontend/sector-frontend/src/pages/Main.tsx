import React from "react";
import HeaderComponent from "../components/HeaderComponent";
import MainComponent from "../components/MainComponent";
import SideComponent from "../components/SideComponent";

const Main = () => {
    return (
        <div className="main__wrapper">
            <MainComponent/>
            <SideComponent/>
        </div>

    );
}

export default Main