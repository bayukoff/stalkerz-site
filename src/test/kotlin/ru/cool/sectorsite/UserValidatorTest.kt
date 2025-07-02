package ru.cool.sectorsite

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.validation.Errors
import ru.cool.sectorsite.mapper.UserMapper
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.model.UserRole
import ru.cool.sectorsite.validator.UserValidator

@ExtendWith(MockitoExtension::class)
class UserValidatorTest {

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var errors: Errors

    @InjectMocks
    private lateinit var validator: UserValidator

    private lateinit var user: User
    private lateinit var userMapper: UserMapper

    @BeforeEach
    fun initUser(){
        user = User("coolz", "cool@512", "qwerty", mutableSetOf(UserRole.ROLE_USER))
        userMapper = UserMapper()
    }

    @Test
    fun `test when user exists in db`(){
        whenever(userService.getByUsername("coolz")).thenReturn(user)
        validator.validate(userMapper.map(user), errors)
        verify(errors).rejectValue("username", "", "Пользователь с таким логином уже зарегистрирован!")
    }

    @Test
    fun `test when user does not exists in db`(){
        whenever(userService.getByUsername("coolz")).thenReturn(null)
        validator.validate(userMapper.map(user), errors)
        verify(errors, never()).rejectValue("username", "", "Пользователь с таким логином уже зарегистрирован!")
    }
}