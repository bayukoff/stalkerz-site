package ru.cool.sectorsite

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import ru.cool.sectorsite.mapper.UserMapper
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.model.UserRole
import ru.cool.sectorsite.service.UserCacheReadService
import ru.cool.sectorsite.service.UserCacheWriteService
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var cacheWriteService: UserCacheWriteService

    @Mock
    private lateinit var cacheReadService: UserCacheReadService

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var correctUser: User
    private lateinit var badUser: User

    @BeforeEach
    fun initUser(){
        correctUser = User("Aboba", "P@ssw0rd", "cool56563@yandex.ru")
        badUser = User("abobik", "123123123", "4el")
    }

    @Test
    fun `test get by email`(){
        whenever(userRepository.findByEmail(correctUser.email)).thenReturn(Optional.of(correctUser))
        whenever(cacheReadService.getByEmail(correctUser.email)).thenReturn(correctUser)
        assertNotNull(userService.getByEmail(correctUser.email))
    }

    @Test
    fun `test get by username`(){
        whenever(userRepository.findByUsername(correctUser.username)).thenReturn(Optional.of(correctUser))
        assertNotNull(userService.getByUsername(correctUser.username))
    }

    @Test
    fun `test get by id`(){
        whenever(userRepository.findById(correctUser.id)).thenReturn(Optional.of(correctUser))
        assertNotNull(userService.getById(correctUser.id))
    }

    @Test
    fun `test load by username`(){
        whenever(userRepository.findByUsername(correctUser.username)).thenReturn(Optional.of(correctUser))
        assertDoesNotThrow { userService.loadUserByUsername(correctUser.username) }
    }

    @Test
    fun `test password encoding when add`(){
        val encodedPassword = "ENCODE"
        val userPassword = correctUser.password
        whenever(passwordEncoder.encode(userPassword)).thenReturn(encodedPassword)
        userService.add(correctUser)
        assertEquals(encodedPassword, correctUser.password)
    }

    @Test
    fun `test save user when not exist`(){
        whenever(userRepository.existsById(correctUser.id)).thenReturn(false)
        whenever(passwordEncoder.encode(correctUser.password)).thenReturn("pass")
        assertTrue { userService.add(correctUser) }
    }

    @Test
    fun `test save user when exists`(){
        whenever(userRepository.existsById(correctUser.id)).thenReturn(true)
        assertFalse {userService.add(correctUser)}
    }

    @Test
    fun `test change password`(){

    }

}