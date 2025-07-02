import Main from "./Main";
import React from "react";
import ShopComponent from "../components/ShopComponent";
import SideComponent from "../components/SideComponent";

function Shop() {
    return (
        <div className="main__wrapper">
            <ShopComponent/>
            <SideComponent/>
        </div>
    );
}

export default Shop;