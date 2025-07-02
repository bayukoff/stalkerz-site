import React, {JSX, ReactNode, use, useEffect, useState} from "react";
import AddCurrencyComponent from "./panel-components/AddCurrencyComponent";
import SetCurrencyComponent from "./panel-components/SetCurrencyComponent";
import ModalWindowComponent from "./ModalWindowComponent";
import {AnimatePresence} from "framer-motion";
import AddNewsComponent from "./panel-components/AddNewsComponent";

function PanelComponent(){

    const [username, setUsername] = useState("")
    const [WindowComponent, setWindowComponent] = useState<null | ((props: any) => JSX.Element)>(null)
    const [isModalOpen, setModalOpen] = useState(false)

    function toggleModalWindow(component: (props: any) => JSX.Element){
        if (!isModalOpen) {
            setModalOpen(true)
            setWindowComponent(() => component)
        }else {
            setModalOpen(false)
            setWindowComponent(null)
        }
    }

    return (
        <div className="main-content">
            <div className="panel">
                <div className="panel_manipulate">
                    <h2>Ник игрока</h2>
                    <input onChange={(event) => setUsername(event.target.value)} className="panel_input" type="text" name="username" id=""/>
                    <div className="panel__buttons">
                        <button style={{marginLeft: "0"}} className="button">БАН</button>
                        <button className="button">РАЗБАН</button>
                        <button onClick={() => {
                            toggleModalWindow(AddCurrencyComponent)
                        }} className="button">Начислить валюту
                        </button>
                        <button onClick={() => {
                            toggleModalWindow(SetCurrencyComponent)
                        }} className="button">Изменить значение валюты
                        </button>
                        <button onClick={() => {
                            toggleModalWindow(AddNewsComponent)
                        }} className="button">Добавить новость
                        </button>
                    </div>
                </div>
                <AnimatePresence>
                    {isModalOpen && WindowComponent ? <ModalWindowComponent><WindowComponent username={username}/></ModalWindowComponent> : null}
                </AnimatePresence>
            </div>
        </div>
    )
}

export default PanelComponent