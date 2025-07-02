import {useAuth} from "../context/AuthContext";
import {useMutationRequest} from "../util/RequestProvider";
import {EnumMethod} from "../util/EnumMethod";

const API_URL: string | undefined = process.env.REACT_APP_API_URL


export function useChangeEmail(email: string){
    const {user, setUser, refresh, getToken} = useAuth()
    const username = user!!.username

    return useMutationRequest(`${API_URL}/users/${username}/changeEmail`, {
        method: EnumMethod.PATCH,
        body: JSON.stringify({username: username, email: email})
    }, true, true, (data) => setUser({username, email, balance: user!!.balance}))
}

export function useChangePassword(password: string){
    const {user, refresh, getToken} = useAuth()
    const username = user?.username

    return useMutationRequest(`${API_URL}/users/${username}/changePassword`, {
        method: EnumMethod.PATCH,
        body: JSON.stringify({username, password})
    }, true, true)
}

export function useSetBalance(username: string, balance: number, isAdd: boolean) {
    const {user} = useAuth()
    if (balance < 0){
        throw new Error("Баланс не должен быть ниже нуля!")
    }

    return useMutationRequest(`${API_URL}/users/${username}/balance`, {
        method: isAdd ? EnumMethod.PATCH : EnumMethod.POST,
        body: JSON.stringify({adminUsername: user!!.username, balance})
    }, true, true)

}

export function useBan(username: string){
    const banUser = async () =>{
        const response = await fetch(`${API_URL}/`)
    }
}