import {useMutation} from "@tanstack/react-query";
import {useAuth} from "../context/AuthContext";
import {MyRequestInit} from "../types/MyRequestInit";
import {ServerMessageType} from "../types/ServerMessageType";

export function useMutationRequest(
    url: string, init: MyRequestInit,
    useRefresh: boolean = false,
    sendRequestTwice: boolean = false,
    onSuccess?: (data: any) => any)
{
    const {refresh, getToken} = useAuth()
    let wasRequest = false

    init.credentials = "include"
    init.headers = { ...init.headers,
        "Authorization": `Bearer ${getToken()}`,
        "Content-Type": "application/json",
    }

    async function request(){
        const response = await fetch(url, init)

        if (!response.ok){
            const responseError = await response.json() as ServerMessageType
            throw new Error(responseError.message)
        }
        if (useRefresh){
            const refreshResponse = await refresh(sendRequestTwice ? () => {
                if (!wasRequest){
                    wasRequest = true
                    request()
                }
            } : undefined)
            if (!refreshResponse.ok) {
                throw new Error("Ошибка токена")
            }
        }
    }

    return useMutation({
        mutationFn: request,
        onSuccess: data => onSuccess ? onSuccess(data) : null
    })

}