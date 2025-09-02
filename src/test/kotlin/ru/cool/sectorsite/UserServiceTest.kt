package ru.cool.sectorsite

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
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
        whenever(cacheReadService.getByEmail(correctUser.email)).thenReturn(correctUser)
        assertNotNull(userService.getByEmail(correctUser.email))
    }

    @Test
    fun `test get by username`(){
        whenever(cacheReadService.getByUsername(correctUser.username)).thenReturn(correctUser)
        assertNotNull(userService.getByUsername(correctUser.username))
    }

    @Test
    fun `test get by id`(){
        whenever(cacheReadService.getById(correctUser.id)).thenReturn(correctUser)
        assertNotNull(userService.getById(correctUser.id))
    }

    @Test
    fun `test load by username`(){
        whenever(cacheReadService.getByUsername(correctUser.username)).thenReturn(correctUser)
        assertDoesNotThrow { userService.loadUserByUsername(correctUser.username) }
    }


    @Test
    fun `test save user when not exist`(){
        whenever(cacheWriteService.add(correctUser)).thenReturn(true)
        assertTrue { userService.add(correctUser) }
    }

    @Test
    fun `test save user when exists`(){
        whenever(cacheWriteService.add(correctUser)).thenReturn(false)
        assertFalse {userService.add(correctUser)}
    }

    @Test
    fun `test change password`(){

    }

}