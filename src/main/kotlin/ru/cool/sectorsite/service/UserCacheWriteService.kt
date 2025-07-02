package ru.cool.sectorsite.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Caching
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.repository.UserRepository

@Service
class UserCacheWriteService(
    private val userRepository: UserRepository,
    private val userCacheReadService: UserCacheReadService,
    @Lazy private val passwordEncoder: PasswordEncoder
): IUserCacheWriteService {

    @CachePut(value = ["users_by_email"], key = "#username")
    override fun updateEmail(newEmail: String, username: String): Boolean{
        val user = userCacheReadService.getByUsername(username)!!
        user.email = newEmail
        return persist(user)
    }

    @CachePut(value = ["users_by_username"], key = "#username")
    override fun updatePassword(newPassword: String, username: String): Boolean{
        val user = userCacheReadService.getByUsername(username)!!
        user.password = passwordEncoder.encode(newPassword)
        return persist(user)
    }

    @Caching(
        evict = [
            CacheEvict(value = ["user_by_username"], key = "#user.username"),
            CacheEvict(value = ["user_by_email"], key = "#user.email"),
            CacheEvict(value = ["user"], key = "#user.id")
        ])
    override fun remove(user: User): Boolean {
        return if(!userCacheReadService.isExistsById(user.id)){
            false
        }else {
            userRepository.delete(user)
            true
        }
    }
    @CachePut(value = ["users"], key = "#user.id")
    override fun add(user: User): Boolean{
        return if (userCacheReadService.isExistsById(user.id)){
            false
        }else{
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
            true
        }
    }
    @CachePut(value = ["users"], key = "#user.id")
    override fun persist(user: User): Boolean{
        return if (userRepository.existsById(user.id)){
            userRepository.save(user)
            true
        }else
            false
    }
}