import React, {use, useContext, useEffect, useState} from "react";
import {Link} from "react-router-dom";
import {AuthContext} from "../context/AuthContext";

const HeaderComponent = () => {

    const{user, wasLoaded} = useContext(AuthContext)
    const[isAuthenticated, setAuthenticated] = useState(false)

    if (!wasLoaded)
        return null

    return (
        <header className="header-wrapper">
            <div className="header-wrapper__title">
                <h1><Link to="/">Сектор 5661</Link></h1>
            </div>
            <div className="header-buttons">
                <div className="header-buttons__button">
                    <Link to="/news">Новости</Link>
                </div>
                <div className="header-buttons__button">
                    <Link to="/shop">Магазин</Link>
                </div>
                {
                    (!user  ?
                        (<div className="header-buttons__button">
                            <Link to="/login">Войти</Link>
                        </div>)
                        :
                        (<div className="header-buttons__button">
                            <Link to={`/users/${user?.username}`}>{user?.username}</Link>
                        </div>)
                    )
                }
            </div>
        </header>

    );
}

export default HeaderComponent;