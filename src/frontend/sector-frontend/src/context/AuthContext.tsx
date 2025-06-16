import {createContext, useContext} from "react";
import {AuthContextType} from "../types/AuthContextType";

export const AuthContext = createContext<AuthContextType | null>(null)

export function useAuth(): AuthContextType {
    const ctx = useContext(AuthContext)
    if (!ctx) {
        throw new Error("useAuth must be used within an AuthProvider")
    }
    return ctx
}