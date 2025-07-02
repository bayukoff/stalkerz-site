import {EnumMethod} from "../util/EnumMethod";

export interface MyRequestInit extends RequestInit{
    method?: EnumMethod
}