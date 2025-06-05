import React, {useEffect, useState} from "react";
import SlideShowComponent from "./SlideShowComponent";
import {NewsType} from "../types/NewsType";
import News from "../pages/News";
import NewsService from "../services/NewsService";

const MainComponent = () => {

    const[news, setNews] = useState<NewsType[]>([])

    // useEffect(() => {
    //     fetch("http://localhost:8080/api/news/someNews")
    //         .then(response => response.json())
    //         .then((data) => {
    //             const withShortText = data.map((nws: NewsType) => {
    //                 const shortNews: NewsType = {...nws}
    //                 shortNews.newsShortText = shortNews.newsText.split(" ", 20).join(" ")
    //                 return shortNews
    //             })
    //             setNews(withShortText)
    //             setNewsLoaded(true)
    //         })
    // }, [])

    useEffect(() => {
        const news = NewsService.getSomeNews(4)
        news.then(
            data => {
                const withShortText = data.map((nws: NewsType) => {
                    const shortNews: NewsType = {...nws}
                    shortNews.newsShortText = shortNews.newsText.split(" ", 20).join(" ")
                    return shortNews
                })
                setNews(withShortText)
            }
        )
    }, []);


    return (
        <main className="main-content">
            <div className="main-content__updates-title">
                <h1 className="updates">
                    Последние обновления
                </h1>
            </div>
                <SlideShowComponent news={news}/>
        </main>
    );
}

export default MainComponent