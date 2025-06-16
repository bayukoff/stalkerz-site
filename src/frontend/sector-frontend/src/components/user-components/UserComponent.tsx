import {useState} from "react";
import {useAuth} from "../../context/AuthContext";
import {useMutation} from "@tanstack/react-query";
import {useNavigate} from "react-router-dom";
import UserAvatarComponent from "./UserAvatarComponent";
import {motion} from "framer-motion";
import UserChangeDataComponent from "./UserChangeDataComponent";

const UserComponent = () => {

    const{logout, user} = useAuth()
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
    const[isHiddenChangeDataFields, setHiddenChangeDataFields] = useState(true)

    return (
        <div className="main-content">
            <motion.div
                layout
            >
                <div className="user">
                    <div className="user_wrapper">
                        <div className="user__info">
                            <UserAvatarComponent/>
                            <div className="user_data">
                                <p className="user_data__text">Позывной: {user?.username}</p>
                                <p className="user_data__text">Почта: {user?.email}</p>
                                <p className="user_data__text">Баланс: 0</p>
                                <p className="change_data__button"
                                   onClick={() => setHiddenChangeDataFields(false)}>Управление аккаунтом</p>
                            </div>
                        </div>
                        <button className="logout_button" onClick={() => {
                            mutate()
                        }}>Выйти
                        </button>
                    </div>
                    <UserChangeDataComponent isHidden={isHiddenChangeDataFields} setHidden={setHiddenChangeDataFields} />
                </div>
            </motion.div>
        </div>
    )
}

export default UserComponent