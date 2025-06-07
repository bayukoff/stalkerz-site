import React, {useEffect, useState} from "react";
import NewsService from "../services/NewsService";
import {useQuery} from "@tanstack/react-query";

/**
 * Просто хочу объявить как функцию, надоели константы
 * @constructor
 */

function NewsComponent(){

    // const[news, setNews] = useState<NewsType[]>()
    const[page, setPage] = useState<number>(0)
    const[pagesAmount, setPagesAmount] = useState<number>(0)
    const{data, error, isLoading, refetch} = useQuery({
        queryKey: ["news", page],
        queryFn: () => NewsService.getNewsOnPage(page),
        enabled: true,
    })

    const changePage = (event: React.MouseEvent) => {
        const btn = event.target as HTMLButtonElement
        const selectedPage = parseInt(btn.value) - 1
        setPage(selectedPage)
    }

    return(
        <div className="main-content">
            <div className="news_wrapper">
                {data?.map((nws, index) => {
                    return (
                        <div id={`${index}`} key={index} className="news">
                            <div className="news-title">
                                <h1>{nws.newsTitle}</h1>
                            </div>
                            <hr/>
                            <div className="news-image">
                                <img className="news-image__img" src={nws.imageUrl} alt={"slide-image " + index}/>
                            </div>
                            <div className="news_text">
                                <p>{nws.newsText}</p>
                            </div>
                        </div>
                    )
                })}
                <div className="news__buttons">
                    {Array(2).fill(null).map((_, i) => {
                            const page = ++i
                            return <button key={i} className="news-button" value={page} onClick={changePage}>
                                {page}
                            </button>
                        }
                    )}
                </div>
            </div>
        </div>
    )
}

export default NewsComponent