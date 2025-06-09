package ru.cool.sectorsite.service

import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.mapper.UserMapper
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.util.UserRole
import ru.cool.sectorsite.util.exception.UserExistsException
import ru.cool.sectorsite.util.exception.UserNotFoundException
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    @Lazy private val passwordEncoder: PasswordEncoder
): UserDetailsService {

    fun getAll(): List<User> = userRepository.findAll()

    fun getById(id: Int): User?{
        val user = userRepository.findById(id)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    fun getByUsername(username: String): User?{
        val user = userRepository.findByUsername(username)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    fun getByEmail(email: String): User?{
        val user = userRepository.findByEmail(email)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = getByUsername(username)
        return if(user != null) MyUserDetails(user) else throw UserNotFoundException()
    }

    fun save(user: User): Boolean{
        if (userRepository.existsById(user.id)){
            return false
        }
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
        return true
    }

    fun remove(user: User): Boolean {
        if (!userRepository.existsById(user.id)){
            return false
        }
        userRepository.delete(user)
        return true
    }

    fun convertToDto(user: User): UserDto = userMapper.map(user)

    fun convertToUser(userDto: UserDto): User = userMapper.map(userDto)
}