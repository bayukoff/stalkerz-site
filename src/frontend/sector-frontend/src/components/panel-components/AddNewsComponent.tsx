import {useAddNews} from "../../services/NewsService";
import {useState} from "react";

const AddNewsComponent = () => {

    const [newsTitle, setNewsTitle] = useState("")
    const [newText, setNewsText] = useState("")
    const [newsImageUrl, setNewsImageUrl] = useState("")

    const{error, isError, isSuccess, mutate} = useAddNews(newsTitle, newText, newsImageUrl)

    return (
        <div className="modal__window_child">
            <h2>Добавить новость</h2>
            <p>Заголовок</p>
            <input onChange={(event) => setNewsTitle(event.target.value)} style={{width: "calc(100% - 15px)"}}
                   type="text" name="" className="panel_input" id=""/>
            <p>Текст</p>
            <textarea onChange={(event) => setNewsText(event.target.value)} style={{width: "calc(100% - 15px)", minHeight: "80px"}}
                   name="" className="panel_input" id=""/>
            <p>URL изображения</p>
            <input onChange={(event) => setNewsImageUrl(event.target.value)} style={{width: "calc(100% - 15px)"}}
                   type="text" name="" className="panel_input" id=""/>
            <button onClick={() => mutate()} style={{marginLeft: 0}} className="button">Добавить</button>
            {isError ? <p className="error">{error?.message}</p> : null}
            {isSuccess ? <p className="success">Новость создана!</p> : null}
        </div>
    )
}

export default AddNewsComponent