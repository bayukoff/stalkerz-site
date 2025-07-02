package ru.cool.sectorsite.mapper

import org.springframework.stereotype.Component
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.model.User

@Component
class UserMapper: Mapper<User, UserDto> {
    override fun map(model: User): UserDto = UserDto(model.username, model.email, model.password, model.role)

}