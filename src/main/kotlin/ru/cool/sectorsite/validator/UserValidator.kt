package ru.cool.sectorsite.validator

import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.service.UserService

@Component
class UserValidator(private val userService: UserService): Validator {
    override fun supports(clazz: Class<*>): Boolean = clazz == UserDto::class.java

    override fun validate(target: Any, errors: Errors) {
        val incomingUserDto = target as UserDto
        var dbUser = userService.getByUsername(incomingUserDto.username)
        if (dbUser != null)
            if (incomingUserDto.username == dbUser.username)
                errors.rejectValue("username", "", "Пользователь с таким логином уже зарегистрирован!")
        if(dbUser == null){
            dbUser = userService.getByEmail(incomingUserDto.email)
            if (incomingUserDto.email == dbUser?.email)
                errors.rejectValue("email", "", "Пользователь с данной почтой уже зарегистрирован!")
        }
    }
}