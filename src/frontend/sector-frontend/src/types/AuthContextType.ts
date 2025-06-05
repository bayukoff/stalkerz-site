import UserDataType from "./UserDataType";

export type AuthContextType = {
    user?: UserDataType | null;
    wasLoaded: boolean,
    login?: (username: string, password: string) => Promise<number>;
    logout?: () => void;
}