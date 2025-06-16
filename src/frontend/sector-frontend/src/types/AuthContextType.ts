import UserType from "./UserType";

export type AuthContextType = {
    user: UserType | null | undefined,
    setUser: (user: UserType | null) => void,
    wasLoaded: boolean,
    login: (username: string, password: string) => Promise<void>,
    logout: () => Promise<number>,
    tkn: string,
    getToken: () => string,
    setTkn: (tkn: string) => void,
    refresh: (callback?: () => any) => Promise<Response>
}