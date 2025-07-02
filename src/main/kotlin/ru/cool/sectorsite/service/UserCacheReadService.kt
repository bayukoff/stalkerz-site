package ru.cool.sectorsite.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository
import kotlin.jvm.optionals.getOrNull

@Service
class UserCacheReadService(
    val userRepository: UserRepository
): IUserCacheReadService {

    fun getAll(): List<User>{
        return userRepository.findAll()
    }

    @Cacheable("users")
    override fun getById(id: Int): User?{
        val user = userRepository.findById(id)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    @Cacheable(value = ["users_by_username"], key = "#username", unless = "#result == null")
    override fun getByUsername(username: String): User?{
        val user = userRepository.findByUsername(username)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    @Cacheable(value = ["users_by_email"], key = "#email", unless = "#result == null")
    override fun getByEmail(email: String): User?{
        val user = userRepository.findByEmail(email)
        return if (user.isPresent) user.get() else user.getOrNull()
    }

    override fun isExistsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun isExistsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    override fun isExistsById(id: Int): Boolean {
        return userRepository.existsById(id)
    }


}