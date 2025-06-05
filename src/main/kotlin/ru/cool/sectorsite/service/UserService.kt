package ru.cool.sectorsite.service

import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.util.UserRole

@Service
class UserService(
    val userRepository: UserRepository,
    @Lazy val passwordEncoder: PasswordEncoder
): UserDetailsService {
    fun getAll(): List<User> = userRepository.findAll()
    fun getById(id: Int) = userRepository.findById(id)
    fun getByUsername(username: String) = userRepository.findByUsername(username)
    fun getByEmail(email: String) = userRepository.findByEmail(email)
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = getByUsername(username!!)
        return MyUserDetails(user!!)
    }
    fun save(user: User){
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }
    fun remove(user: User) = userRepository.delete(user)

    fun convertToDto(user: User?): UserDto?{
        if(user != null){
            return UserDto(user.username, user.email, user.password)
        }
        return null
    }

    fun convertToUser(userDto: UserDto) = User(
        username = userDto.username,
        email = userDto.email!!,
        password = userDto.password,
        role = UserRole.ROLE_USER)
}