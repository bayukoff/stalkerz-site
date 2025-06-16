import UserType from "../types/UserType";

const API_URL = process.env.REACT_APP_API_URL

export async function registerUser(userData: UserType, refresh: () => Promise<Response>){
    const response = await fetch(`${API_URL}/users/new`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(userData),
    });

    if (!response.ok) {
        const message = await response.json() as {message: string};
        const refreshResponse = await refresh()
        if (!refreshResponse.ok)
            throw new Error(message.message || "Ошибка при регистрации");

    }

    return await response.json();
}
