import {useState} from "react";
import {useSetBalance} from "../../services/UserService";

const AddCurrencyComponent = (props: {username: string}) => {

    const [balance, setBalance] = useState(0)
    const {mutate, error, isError, isSuccess} = useSetBalance(props.username, balance, true)

    return (
        <div className="modal__window_child">
            <h2>Начисление валюты игроку</h2>
            <p>Количество:</p>
            <input onChange={(event) => setBalance(parseInt(event.target.value))} style={{width: "calc(100% - 15px)"}} type="text" name="" className="panel_input" id=""/>
            <button onClick={() => mutate()} style={{marginLeft: 0}} className="button">Начислить!</button>
            {isError ? <p className="error">{error?.message}</p> : null}
            {isSuccess ? <p className="success">Валюта начислена!</p> : null}
        </div>
    )
}

export default AddCurrencyComponent