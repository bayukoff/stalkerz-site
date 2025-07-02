import {NewsType} from "../types/NewsType";
import {useAuth} from "../context/AuthContext";
import {useMutationRequest} from "../util/RequestProvider";
import {EnumMethod} from "../util/EnumMethod";


const API_URL = process.env.REACT_APP_API_URL

export async function getAllNews(): Promise<NewsType[]>{
    return await fetch(`${(API_URL)}/news`)
        .then(response => response.json() as Promise<NewsType[]>)
}

export async function getSomeNews(amount: number): Promise<NewsType[]>{
    return await fetch(`${(API_URL)}/news/someNews/${amount}`)
        .then(response => response.json() as Promise<NewsType[]>)
}

export async function getNewsOnPage(page: number): Promise<NewsType[]>{
    return await fetch(`${(API_URL)}/news/pages/${page}`)
        .then(response => response.json())
}

export async function getPagesAmount(): Promise<number>{
    return await fetch(`${(API_URL)}/news/pages`)
        .then(response => response.json() as Promise<number>)
        .then(data => Math.ceil(data / 8))
}

export function useAddNews(newsTitle: string, newsText: string, imageUrl: string){
    // const{getToken} = useAuth()
    return useMutationRequest(`${API_URL}/news/new`, {
        method: EnumMethod.POST,
        body: JSON.stringify({newsTitle, newsText, imageUrl}),
        headers: {
            "Content-Type": "Application/json"
        }
    })

    // const addNews = async () => {
    //     const response = await fetch(`${API_URL}/news/new`, {
    //         method: "POST",
    //         headers: {
    //             "Content-Type": "application/json",
    //             "Authorization": `Bearer ${getToken()}`
    //         },
    //         body: JSON.stringify({newsTitle, newsText, imageUrl}),
    //         credentials: "include"
    //     })
    //     console.log(`${newsTitle} ${newsText} ${imageUrl}`)
    //     if (!response.ok){
    //         const error = await response.json() as ServerMessageType
    //         throw new Error(error.message)
    //     }
    // }
    // return useMutation({
    //     mutationFn: async () => await addNews()
    // })
}