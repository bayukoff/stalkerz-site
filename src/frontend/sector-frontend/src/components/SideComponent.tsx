import React, {useEffect, useRef, useState} from "react";

const SideComponent = () => {

    const[online, setOnline] = useState(50)
    const currentOnline = useRef<HTMLSpanElement>(null)
    // useEffect(()=>{
    //     if (currentOnline.current != null)
    //         currentOnline.current.style.width = `${online}%`
    // }, [online])

    return (
        <aside className="aside">
            <div className="aside__content">
                <div className="aside-title">
                    <h2>Онлайн</h2>
                    <div className="online-wrapper">
                        <div className="online-bar">
                            <div className="online-bar__progress-bar" style={{width: `${online}%`, transition: ".3s"}}></div>
                            <div className="online-players">
                                <span ref={currentOnline} className="current-online">{online}</span>/<span className="max-online">100</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="aside__content">
                <div className="aside-title">
                    <h2><a target="_blank" href="https://t.me/sector5661">Telegram-канал</a></h2>
                </div>
            </div>
            <div className="aside__content">
                <div className="aside-title">
                    <h2><a target="_blank" href="https://www.youtube.com/@KotenokDashi">Youtube</a></h2>
                </div>
            </div>

        </aside>
    );
}

export default SideComponent