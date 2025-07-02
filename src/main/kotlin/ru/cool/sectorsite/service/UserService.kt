package ru.cool.sectorsite.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.mapper.UserMapper
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.util.exception.UserNotFoundException

@Service
class UserService(
    private val readService: UserCacheReadService,
    private val writeService: UserCacheWriteService,
    private val userMapper: UserMapper
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = readService.getByUsername(username)
        return if(user != null) MyUserDetails(user) else throw UserNotFoundException()
    }

    fun getAll(): List<User>{
        return readService.getAll()
    }

    fun getAllDto(): List<UserDto>{
        return getAll().map { userMapper.map(it) }
    }

    fun getById(id: Int): User {
        val user = readService.getById(id) ?: throw UserNotFoundException()
        return user
    }

    fun getByUsername(username: String): User? {
        val user = readService.getByUsername(username)
        return user
    }

    fun getByEmail(email: String): User? {
        val user = readService.getByEmail(email)
        return user
    }

    fun isExistsByUsername(username: String): Boolean {
        return readService.isExistsByUsername(username)
    }

    fun isExistsByEmail(email: String): Boolean {
        return readService.isExistsByEmail(email)
    }

    fun isExistsById(id: Int): Boolean{
        return readService.isExistsById(id)
    }

    fun add(user: User): Boolean {
        return writeService.add(user)
    }

    fun persist(user: User): Boolean {
        return writeService.persist(user)
    }

    fun updateEmail(newEmail: String, username: String): Boolean{
        return writeService.updateEmail(newEmail, username)
    }

    fun updatePassword(newPassword: String, username: String): Boolean{
        return writeService.updatePassword(newPassword, username)
    }

    fun remove(user: User): Boolean {
        return writeService.remove(user)
    }

}