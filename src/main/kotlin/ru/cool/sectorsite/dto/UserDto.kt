package ru.cool.sectorsite.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import ru.cool.sectorsite.model.UserRole

data class UserDto(
    @field:NotEmpty(message = "Имя пользователя не должно быть пустым!")
    @field:Size(min = 4, max = 20)
    @field:Pattern(
        regexp = "^[A-Z][a-z]{3,12}",
        message = "Логин должен состоять из заглавной буквы и должен содержать максимум 20 символов!")
    val username: String,
    @field:Email(message = "Неправильный формат email!")
    @field:NotEmpty(message = "Поле с почтой не долнжно быть пустым!")
    val email: String = "",
    @field:Pattern(
        regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@!#$%^&*]).{8,}",
        message = "Пароль должен содержать заглавную букву, цифры и знаки !@#$%^&")
    @field:NotEmpty
    val password: String = "",
    val role: MutableSet<UserRole> = mutableSetOf(),
    val balance: Int = 0
)
