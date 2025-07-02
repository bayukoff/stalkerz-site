import React, {useEffect, useState} from "react";
import {useQuery} from "@tanstack/react-query";
import {getNewsOnPage} from "../services/NewsService";
import {motion, AnimatePresence} from "framer-motion";

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
        queryFn: () => getNewsOnPage(page),
        enabled: true,
    })

    const changePage = (event: React.MouseEvent) => {
        const btn = event.target as HTMLButtonElement
        const selectedPage = parseInt(btn.value) - 1
        setPage(selectedPage)
    }

    const containerVariants = {
        hidden: {
            opacity: 0,
            height: 0,
            minHeight: 0
        },
        visible: {
            height: 'auto',
            minHeight: "900px",
            opacity: 1,
            transition: {
                opacity: {duration: 0.2},
                height: {duration: 0.2},
                minHeight: {duration: 0.2},
                when: 'beforeChildren',
                delayChildren: 2
            }
        }
    }

    const childrenVariants = {
        hidden: {
            opacity: 0,
            height: 0,
        },
        visible: {
            height: 'auto',
            opacity: 1,
            transition: {
                opacity: {duration: 0.2, delay: 0.4},
                height: {duration: 2, delay: 0.4},
            }
        }
    }

    return(
        <AnimatePresence>
            <motion.div layout variants={containerVariants} initial="hidden" animate="visible" className="main-content">
                <motion.div variants={childrenVariants} className="news_wrapper">
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
                                return <button key={i} className="button" style={{width: "60px", height: "60px", borderRadius: "50%"}} value={page} onClick={changePage}>
                                    {page}
                                </button>
                            }
                        )}
                    </div>
                </motion.div>
            </motion.div>
        </AnimatePresence>
    )
}

export default NewsComponent