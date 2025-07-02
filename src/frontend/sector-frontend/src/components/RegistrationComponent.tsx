import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useMutation} from "@tanstack/react-query";
import {registerUser} from "../services/AuthService";
import {useAuth} from "../context/AuthContext";

const RegistrationComponent = () => {

    const {refresh} = useAuth()
    const[login, setLogin] = useState<string>('')
    const[email, setEmail] = useState<string>('')
    const[password, setPassword] = useState<string>('')
    const[passwordConfirmation, setPasswordConfirmation] = useState<string>('')
    const[isPasswordCorrect, setPasswordCorrect] = useState(true);

    const{isSuccess, isError, mutate, error} = useMutation({
        mutationFn: () => registerUser({username: login, email, password, balance: 0}, refresh),
    })
    const navigate = useNavigate()

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        mutate()
        if (isSuccess)
            navigate("/")
    }

    const confirmPassword = () => {
        if (password !== passwordConfirmation){
            setPasswordCorrect(false)
            return
        }
        setPasswordCorrect(true)
    }

    useEffect(() => {

    }, []);

    return (
          <div className="main-content">
              <h2 style={{textAlign: "center"}}>Регистрация</h2>
              <div className="registration">
                  <form onSubmit={(event)=> {
                      if (!isPasswordCorrect)
                          event.preventDefault()
                      handleSubmit(event)
                  }} className="standard-form" method="post">
                      {!isSuccess &&
                          (<>
                              <p>Ваш логин будет использоваться в игре в качестве никнейма</p>
                              <input className="form-input" type="text" placeholder="Логин" value={login}
                                     onChange={(e) => setLogin(e.target.value)}/>
                              <input className="form-input" type="email" placeholder="Email" value={email}
                                     onChange={(e) => setEmail(e.target.value)}/>
                              <input className="form-input" type="password" placeholder="Пароль" value={password}
                                     onChange={(e) => setPassword(e.target.value)}/>
                              <input className="form-input" type="password" placeholder="Подтвердите пароль"
                                     value={passwordConfirmation}
                                     onChange={(e) => setPasswordConfirmation(e.target.value)}/>
                              {
                                  error ? (
                                      <ul className="registration-error">
                                          {
                                              error.message.split("\n").map((line, idx) =>
                                                  line !== "" ? <li key={idx}>{line}</li> : (!isPasswordCorrect ? <li>Пароли разные!</li> : null)
                                              )
                                          }
                                      </ul>
                                  ) : null
                              }
                              <input className="button" type="submit" onClick={confirmPassword}/>
                          </>
                      )}
                      {isSuccess && (<p style={{color: 'green'}}>Регистрация прошла успешно!</p>)}
                  </form>
              </div>
          </div>
    );
}

export default RegistrationComponent