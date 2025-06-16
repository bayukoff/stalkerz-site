import {ReactNode, useEffect, useState} from "react";
import UserType from "../types/UserType";
import {AuthContext} from "../context/AuthContext";
import {EnumRole} from "../enum/EnumRole";

type AuthProviderProps = {
    children: ReactNode;
}

export const AuthProvider = ({children}: AuthProviderProps) => {

    const API_URL = process.env.REACT_APP_API_URL

    const[user, setUser] = useState<UserType | null>()
    const[tkn, setTkn] = useState<string>("")
    const[wasLoaded, setWasLoaded] = useState<boolean>(false)

    const getToken = () => {
        return tkn
    }

    const refresh = async (callback?: () => any) => {
        const response = await fetch(`${API_URL}/auth/refresh`, {
            credentials: "include"
        })
        if (!response.ok) {
            setWasLoaded(true)
            return response
        }
        const data = await response.json() as {accessToken: string}
        const accessToken = data.accessToken
        setTkn(accessToken)
        const decoded = JSON.parse(atob(accessToken.split(".")[1]))
        setUser({username: decoded.username, email:decoded.email, role: decoded.roles.map((role: string, index: number) => EnumRole[index])})
        if (callback != null)
            callback()
        setWasLoaded(true)
        return response
    }

    useEffect(() => {
        refresh()
    }, []);

    /**
     * Метод для аунтентификации пользователя, который передастся в контекст, и из контекста может быть использован в любом месте
     * @param username - логин пользователя
     * @param password - пароль соответственно
     */

    const login = async (username: string, password: string) => {
        const response = await fetch(`${API_URL}/auth`, {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({username, password}),
            credentials: "include"
        })
        if (!response.ok) {
            const errorMessage = await response.json()
            throw Error(errorMessage.message)
        }
        const data = await response.json()
        const accessToken = data.accessToken
        const decoded = JSON.parse(atob(accessToken.split(".")[1]))
        setTkn(accessToken)
        setUser({username: decoded.username, email:decoded.email, role: decoded.roles.map((role: string, index: number) => EnumRole[index])})
    }

    const logout = async () => {
        const response = await fetch(`${API_URL}/auth/logout`, {
            method: "POST",
            headers: {
                "Context-Type": "application/json",
                "Authorization": `Bearer ${tkn}`
            },
            credentials: "include"
        })
        if(!response.ok) {
            try {
                const refreshResponse = await refresh()
                if (refreshResponse.ok)
                    console.log("success")
            }catch (e){
                throw new Error("Ошибка токена!")
            }
        }
        setUser(null)
        setTkn("")
        return response.status;
    }

    return (
        <AuthContext.Provider value={{user, setUser, wasLoaded, login, logout, tkn, getToken, setTkn, refresh}}>
            {children}
        </AuthContext.Provider>
    )
}