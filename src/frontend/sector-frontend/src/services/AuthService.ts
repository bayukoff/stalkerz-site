import UserDataType from "../types/UserDataType";

class AuthService{
    private static API_URL = process.env.REACT_APP_API_URL

    static async registerUser(userData: UserDataType){
        const response = await fetch(`${this.API_URL}/users/new`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(userData),
        });

        if (!response.ok) {
            const message = await response.json() as {message: string}; // получаем текст ошибки
            throw new Error(message.message || "Ошибка при регистрации");
        }

        return await response.json();
    }
}

export default AuthService