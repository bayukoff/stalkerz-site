import React, {useContext, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import UserDataType from "../types/UserDataType";
import {AuthContext} from "../context/AuthContext";


const LoginComponent = () => {
    const {login} = useContext(AuthContext)
    const[username, setUsername] = useState('')
    const[password, setPassword] = useState('')
    const[authStatus, setAuthStatus] = useState<number>()

    const navigate = useNavigate()

    const handleRequest = async (event: React.FormEvent) => {
        event.preventDefault()

        const userData:UserDataType = {username: username, password: password}

        let status: number = 0;
        if (login) {
            status = await login(userData.username, userData.password!!);
        }
        setAuthStatus(status)
        if (status === 200)
            navigate("/")
        else
            throw Error("Ошибка аутентификации: " + status)
    }

    return(
        <div className="main-content">
                <div>
                    <h2 style={{textAlign: "center"}}>Вход в учетную запись</h2>
                </div>
                <div className="login-form">
                    <form onSubmit={handleRequest} style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        margin: "0 auto",
                        width: "20%"
                    }}>
                        <input className="form-input" type="text" placeholder="Логин" onChange={(event) => setUsername(event.target.value)}/>
                        <input className="form-input" type="password" placeholder="Пароль" onChange={(event) => setPassword(event.target.value)}/>
                        <input className="form-submit" type="submit" value="Войти"/>
                    </form>
                </div>
                <div style={{display: "flex", justifyContent: "center", flexDirection: "column", alignItems: "center"}}>
                    <h3>Нет аккаунта?</h3>
                    <Link to="/registration"><button className="form-submit">Зарегистрироваться!</button></Link>
                </div>
        </div>
    )
}

export default LoginComponent