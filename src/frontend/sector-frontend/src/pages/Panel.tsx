import SideComponent from "../components/SideComponent";
import React from "react";
import PanelComponent from "../components/PanelComponent";

function Panel(){
    return(
        <div className="main__wrapper">
            <PanelComponent/>
            <SideComponent/>
        </div>
    )
}

export default Panel