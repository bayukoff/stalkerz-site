import React, {useEffect, useState} from "react";
import SlideShowComponent from "./SlideShowComponent";
import {NewsType} from "../types/NewsType";
import News from "../pages/News";
import {getSomeNews} from "../services/NewsService";
import {AnimatePresence, motion} from "framer-motion";
import {containerShow} from "../util/AnimationVariants";

const MainComponent = () => {

    const[news, setNews] = useState<NewsType[]>([])

    useEffect(() => {
        const news = getSomeNews(4)
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
        <AnimatePresence>
            <motion.main layout variants={containerShow()} className="main-content">
                <div className="main-content__updates-title">
                    <h1 className="updates">
                        Последние обновления
                    </h1>
                </div>
                    <SlideShowComponent news={news}/>
            </motion.main>
        </AnimatePresence>
    );
}

export default MainComponent