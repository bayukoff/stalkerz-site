import {useMutation} from "@tanstack/react-query";
import {useAuth} from "../context/AuthContext";
import UserType from "../types/UserType";

const API_URL: string | undefined = process.env.REACT_APP_API_URL


export function useChangeEmail(email: string){
    const {user, setUser, refresh, getToken} = useAuth()
    const username = user?.username
    const changeEmail = async () => {
        const currentToken = getToken()
        const response = await fetch(`${API_URL}/users/${username}/changeEmail`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${currentToken}`
            },
            body: JSON.stringify({username: username, email: email}),
            credentials: "include"
        })
        if (!response.ok) {
            const error = await response.json()
            throw new Error(error.message)
        }
        setUser({username: username!!, email})
        return await response.json() as UserType
    }
    return useMutation({
        mutationFn: changeEmail,
        onError: async error => {
            const refreshResponse = await refresh(changeEmail)
            if (!refreshResponse.ok){
                throw error
            }
        }
    })
}

export function useChangePassword(password: string){
    const {user, refresh, getToken} = useAuth()
    const username = user?.username
    const changePassword = async () => {
        const currentToken = getToken()
        const response = await fetch(`${API_URL}/users/${username}/changePassword`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${currentToken}`
            },
            body: JSON.stringify({username, password}),
            credentials: "include"
        })
        if (!response.ok){
            const error = await response.json()
            throw new Error(error)
        }
    }
    return useMutation(
        {
            mutationFn: changePassword,
            onError: async error => {
                const refreshResponse = await refresh(changePassword)
                if (refreshResponse.ok)
                    await changePassword()
                throw new Error(error.message)
            }
        }
    )
}