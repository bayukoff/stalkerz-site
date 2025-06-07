import {ReactNode, useEffect, useState} from "react";
import UserDataType from "../types/UserDataType";
import {AuthContext} from "../context/AuthContext";

type AuthProviderProps = {
    children: ReactNode;
}

export const AuthProvider = ({children}: AuthProviderProps) => {

    const API_URL = process.env.REACT_APP_API_URL

    const[user, setUser] = useState<UserDataType | null>()
    const[wasLoaded, setWasLoaded] = useState<boolean>(false)

    useEffect(() => {
        fetch(`${API_URL}/auth/check`, {
            credentials: "include"
        })
            .then(response => {
                if (response.ok)
                    return response.json() as Promise<UserDataType>
                return
            })
            .then(data => {
                if (data !== undefined) {
                    setUser({username: data.username})
                    setWasLoaded(true)
                }
            })
            .finally(() => {
                setWasLoaded(true)
            })
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
            throw Error("Аунтентификация не удалась!")
        }
        const data = await response.json() as UserDataType
        setUser({username: data.username})
        return response.status
    }


    const logout = async () => {
        const response = await fetch(`${API_URL}/auth/logout`, {
            method: "POST",
            credentials: "include"
        })
        if(!response.ok) {
            throw Error("Ошибка при логаунте")
        }
        setUser(null)
        return response.status;
    }

    return (
        <AuthContext.Provider value={{user, wasLoaded, login, logout}}>
            {children}
        </AuthContext.Provider>
    )
}