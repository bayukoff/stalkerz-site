import {EnumRole} from "../enum/EnumRole";

type UserType = {
    username: string,
    email?: string,
    password?: string,
    role?: EnumRole
}

export default UserType