import {use, useContext} from "react";
import {AuthContext} from "../context/AuthContext";
import {useMutation} from "@tanstack/react-query";
import {useNavigate} from "react-router-dom";

const UserComponent = () => {

    const{logout, user} = useContext(AuthContext)
    const{mutate} = useMutation({
        mutationFn: () => {
            if (logout !== null && logout !== undefined)
                return logout()
            else throw Error("Не удалось выйти из аккаунта")
        },
        onSuccess: () => {
            nav("/")
        }
    })
    const nav = useNavigate()

    return (
        <div className="main-content">
            <div className="user">
                <button onClick={ () => {
                    mutate()
                }}>Выйти</button>
                <h1>{user?.username}</h1>
            </div>
        </div>
    )
}

export default UserComponent