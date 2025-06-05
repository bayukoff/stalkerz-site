import React from "react";
import SideComponent from "../components/SideComponent";
import NewsComponent from "../components/NewsComponent";

const News = () => {
    return (
        <div className="main__wrapper">
            <NewsComponent/>
            <SideComponent/>
        </div>
    );
}

export default News