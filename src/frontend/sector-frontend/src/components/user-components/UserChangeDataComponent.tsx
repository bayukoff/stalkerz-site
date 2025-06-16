import {AnimatePresence, motion} from "framer-motion";
import React, {ReactNode, useState} from "react";
import UserChangeEmailComponent from "./UserChangeEmailComponent";
import UserChangePasswordComponent from "./UserChangePasswordComponent";

function UserChangeDataComponent(props: {isHidden: boolean, setHidden: Function}){

    const[isEmailFieldHidden, setEmailFieldHidden] = useState(true)
    const[isPasswordFieldHidden, setPasswordFieldHidden] = useState(true)

    const variants = {
        hidden: {
            height: 0,
            opacity: 0,
        },
        visible: {
            height: 'auto',
            opacity: 1,
            transition: {
                height: { duration: 0.4 },
                opacity: { duration: 0.2, delay: 0.3 }, // задержка на прозрачность
            },
        },
        exit: {
            opacity: 0,
            height: 0,
            transition: {
                opacity: { duration: 0.2 },
                height: { duration: 0.3, delay: 0.1 },
            },
        },
    };

    function hideAll(){
        props.setHidden(true)
        setEmailFieldHidden(true)
        setPasswordFieldHidden(true)
    }

    const HideButton = () => {
        return (
            <p className="change_data__button hide" onClick={hideAll}>
                Скрыть
            </p>
        )
    }

    const AnimationDiv = ({children}: any) => {
        return (
            <motion.div layout
                        variants={variants}
                        initial="hidden"
                        animate="visible"
                        exit="exit"
                        transition={{
                            opacity: { duration: 0.2 },
                            height: { duration: 0.4, delay: 0.2}
                        }}
                        className="user_changers"
            >
                {children}
            </motion.div>
        )
    }

    return (
        <AnimatePresence mode="wait">
            {!props.isHidden && isEmailFieldHidden && isPasswordFieldHidden ?
                (
                    <AnimationDiv>
                        <p className="change_data__button change_email" onClick={() => setEmailFieldHidden(false)}>Сменить электронную почту</p>
                        <p className="change_data__button change_password" onClick={() => setPasswordFieldHidden(false)}>Сменить пароль</p>
                        <HideButton/>
                    </AnimationDiv>
                ) : (!isEmailFieldHidden ? <AnimationDiv><UserChangeEmailComponent/><HideButton/></AnimationDiv> :
                    (!isPasswordFieldHidden ? <AnimationDiv><UserChangePasswordComponent/><HideButton/></AnimationDiv> : null))

            }
        </AnimatePresence>
    )
}

export default UserChangeDataComponent