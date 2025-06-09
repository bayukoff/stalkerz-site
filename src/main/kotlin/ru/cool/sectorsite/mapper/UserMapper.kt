package ru.cool.sectorsite.mapper

import org.springframework.stereotype.Component
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.util.UserRole
import ru.cool.sectorsite.util.exception.UserNotFoundException

@Component
class UserMapper: Mapper<User, UserDto> {
    override fun map(model: User): UserDto = UserDto(model.username, model.email, model.password)

    fun map(dto: UserDto): User = User(dto.username, dto.email, dto.password, UserRole.ROLE_USER)

}