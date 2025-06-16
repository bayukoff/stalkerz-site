import {useChangeEmail} from "../../services/UserService";
import {FormEvent, RefObject, useRef, useState} from "react";

function UserChangeEmailComponent(){

    const emailField: RefObject<HTMLInputElement | null> = useRef(null)
    const [email, setEmail] = useState("")
    const mutation = useChangeEmail(email)

    function changeUserEmail(event: FormEvent){
        event.preventDefault()
        const email = emailField.current?.value
        if (email != null) {
            setEmail(email)
            mutation.mutate()
        }
    }

    return (
        <form onSubmit={(event) => changeUserEmail(event)} className="change_data__form">
            <label htmlFor="change_email">Новый адрес:</label>
            <input ref={emailField} className="change_data__input" id="change_email" type="email"/>
            <input className="change_data__submit" type="submit" value="Изменить"/>
            {mutation.isSuccess ? <div className="status success">Почта успешно изменена!</div> : null}
            {mutation.isError && !mutation.isSuccess ?
                <div className="status error">
                    {mutation.error.message}
                </div> : null
            }
        </form>
    )
}

export default UserChangeEmailComponent