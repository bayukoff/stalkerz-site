import React, {useContext, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext";
import {useMutation} from "@tanstack/react-query";


const LoginComponent = () => {
    const {login} = useAuth()
    const[username, setUsername] = useState('')
    const[password, setPassword] = useState('')
    const{error, mutate, isError} = useMutation({
        mutationFn: async () => login!!(username, password),
        onSuccess: data => navigate("/")
    })

    const navigate = useNavigate()

    const handleRequest = async (event: React.FormEvent) => {
        event.preventDefault()
        mutate()
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
                        {isError ? <p className="status error">{error.message}</p> : null}
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