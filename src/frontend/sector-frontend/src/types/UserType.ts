import {EnumRole} from "../enum/EnumRole";

type UserType = {
    username: string,
    email?: string,
    password?: string,
    balance: number
    role?: EnumRole[],
}

export default UserType