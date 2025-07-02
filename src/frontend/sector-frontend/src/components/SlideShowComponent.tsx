import React, {useRef, useState} from "react";
import {NewsType} from "../types/NewsType";
import {HashLink} from "react-router-hash-link"
import {AnimatePresence, motion} from "framer-motion";
import {containerShow} from "../util/AnimationVariants";

type NewsArray = {
    news: NewsType[];
}

const SlideShowComponent = ({news}: NewsArray) => {

    const slideshow = useRef<HTMLDivElement>(null)
    const[currentSlide, setCurrentSlide] = useState<number>(0)


    const swapSlideLeft = () => {
        setCurrentSlide((prev) => Math.max(prev - 1, 0));
    }

    const swapSlideRight = () => {
        setCurrentSlide((prev) => Math.min(prev + 1, news.length - 1));
    }

    return (
        <AnimatePresence>
            <motion.div layout variants={containerShow()} className="slideshow__wrapper">
                <div className="slideshow-buttons">
                    <div onClick={swapSlideLeft}  className="slideshow-buttons__button button_left">←</div>
                    <div onClick={swapSlideRight}  className="slideshow-buttons__button button_right">→</div>
                </div>
                <div ref={slideshow} className="slideshow">
                    <div className="slides__wrapper" style={{
                        transform: `translateX(calc(${150 - currentSlide * 100}%))`
                    }}>
                        {news.map((nws, index) => {
                            return (
                                <div key={index} className="slideshow__slide">
                                    <div className="slide-image">
                                        <img className="slide-image__img" src={nws.imageUrl} alt={"slide-image " + index}/>
                                    </div>
                                    <div className="slide__description">
                                        <div className="slide-title">
                                            <h2>{nws.newsTitle}</h2>
                                        </div>
                                        <div className="slide_text">
                                            <p>{nws.newsShortText} <span className="read-more"><HashLink to={`/news/#${index}`}>читать далее</HashLink></span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </motion.div>
        </AnimatePresence>
    );
}

export default SlideShowComponent
