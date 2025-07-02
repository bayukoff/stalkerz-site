package ru.cool.sectorsite

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository
import ru.cool.sectorsite.service.UserCacheReadService
import ru.cool.sectorsite.service.UserCacheWriteService
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class UserCacheWriteServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userCacheReadService: UserCacheReadService

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var service: UserCacheWriteService

    private lateinit var user: User

    @BeforeEach
    fun initUser(){
        user = User("Coolz", "!Cool12391257", "cool56563@yandex.ru")
    }

    @Test
    fun `test update email`(){
        whenever(userCacheReadService.getByUsername(user.username)).thenReturn(user)
        whenever(service.persist(user)).thenReturn(true)
        assertTrue {
            service.updateEmail(user.email, user.username)
        }
    }

    @Test
    fun `test update password`(){
        val oldPassword = user.password
        val newRawPassword = "new-password"
        val newEncodedPassword = "new-encoded-password"
        whenever(userCacheReadService.getByUsername(user.username)).thenReturn(user)
        whenever(passwordEncoder.encode(newRawPassword)).thenReturn(newEncodedPassword)
        service.updatePassword(newRawPassword, user.username)
        assertNotEquals(oldPassword, user.password)
    }

    @Test
    fun `test remove user when not exists`(){
        whenever(userCacheReadService.isExistsById(user.id)).thenReturn(false)
        assertFalse {
            service.remove(user)
        }
    }

    @Test
    fun `test remove user when exists`(){
        whenever(userCacheReadService.isExistsById(user.id)).thenReturn(true)
        assertTrue {
            service.remove(user)
        }
    }

    @Test
    fun `test add user when exists`(){
        whenever(userCacheReadService.isExistsById(user.id)).thenReturn(true)
        assertFalse{
            service.add(user)
        }
    }

    @Test
    fun `test add user when not exists`(){
        whenever(userCacheReadService.isExistsById(user.id)).thenReturn(false)
        whenever(passwordEncoder.encode(user.password)).thenReturn("encoded")
        assertTrue{
            service.add(user)
        }
    }


}