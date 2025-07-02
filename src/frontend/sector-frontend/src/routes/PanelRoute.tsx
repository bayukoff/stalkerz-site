import {useAuth} from "../context/AuthContext";
import {Navigate} from "react-router-dom";
import {EnumRole} from "../enum/EnumRole";

const PanelRoute = ({children}: any) => {
    const{wasLoaded, user} = useAuth()
    if (!wasLoaded)
        return null
    if (user && user.role?.includes(EnumRole.ROLE_ADMIN) )
        return children
    else return <Navigate to={"/"}/>
}
export default PanelRoute