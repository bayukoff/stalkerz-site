import {FormEvent, RefObject, useRef, useState} from "react";
import {useChangePassword } from "../../services/UserService";

function UserChangePasswordComponent(){
    
    const passwordField: RefObject<HTMLInputElement | null> = useRef(null)
    const confirmPasswordField: RefObject<HTMLInputElement | null> = useRef(null)
    const [passwordsIncorrect, setPasswordsIncorrect] = useState<boolean>(false)
    const [password, setPassword] = useState("")
    const mutation = useChangePassword(password)

    function changeUserPassword(event: FormEvent){
        event.preventDefault()
        const password = passwordField.current?.value
        const confirmPassword = confirmPasswordField.current?.value

        if (password !== confirmPassword)
            setPasswordsIncorrect(true)
        if (password != null) {
            setPassword(password)
            mutation.mutate()
        }
    }

    return (
        <form onSubmit={(event) => changeUserPassword(event)} className="change_data__form">
            <label htmlFor="change_password">Новый пароль:</label>
            <input ref={passwordField} className="change_data__input" id="change_password" type="password"/>
            <label htmlFor="confirm_password" style={{marginTop: "10px"}}>Подтверждение пароля:</label>
            <input ref={confirmPasswordField} id="confirm_password" className="change_data__input" type="password"/>
            <input className="change_data__submit" type="submit" value="Изменить"/>
            {mutation.isSuccess ? <div className="status success">Пароль успешно изменён!</div> : null}
            {mutation.isError && !mutation.isSuccess ?
                <div className="status error">
                    {mutation.error.message}
                </div> : null
            }
            {passwordsIncorrect ?
                <div className="status error">
                    Пароли разные!
                </div> : null
            }
        </form>
    )
}

export default UserChangePasswordComponent