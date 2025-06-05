import {NewsType} from "../types/NewsType";

class NewsService{

    private static API_URL = process.env.REACT_APP_API_URL

    static async getAllNews(): Promise<NewsType[]>{
        return await fetch(`${(this.API_URL)}/news`)
            .then(response => response.json() as Promise<NewsType[]>)
    }

    static async getSomeNews(amount: number): Promise<NewsType[]>{
        return await fetch(`${(this.API_URL)}/news/someNews/${amount}`)
            .then(response => response.json() as Promise<NewsType[]>)
    }

    static async getNewsOnPage(page: number): Promise<NewsType[]>{
        return await fetch(`${(this.API_URL)}/news/pages/${page}`)
            .then(response => response.json())
    }

    static async getPagesAmount(): Promise<number>{
        return await fetch(`${(this.API_URL)}/news/pages`)
            .then(response => response.json() as Promise<number>)
            .then(data => Math.ceil(data / 8))
    }
}

export default NewsService