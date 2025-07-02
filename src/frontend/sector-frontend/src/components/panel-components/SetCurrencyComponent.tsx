import {useState} from "react";
import {useSetBalance} from "../../services/UserService";

const SetCurrencyComponent = (props: {username: string}) => {

    const [balance, setBalance] = useState(0)
    const {mutate, error, isError, isSuccess} = useSetBalance(props.username, balance, false)

    return (
        <div className="modal__window_child">
            <h2>Изменение значения валюты игрока</h2>
            <p>Количество:</p>
            <input onChange={(event) => setBalance(parseInt(event.target.value))} style={{width: "calc(100% - 15px)"}} type="text" name="" className="panel_input" id=""/>
            <button style={{marginLeft: 0}} className="button" onClick={() => mutate()}>Начислить!</button>
            {isError ? <p className="error">{error?.message}</p> : null}
            {isSuccess ? <p className="success">Баланс изменён!</p> : null}
        </div>
    )
}

export default SetCurrencyComponent