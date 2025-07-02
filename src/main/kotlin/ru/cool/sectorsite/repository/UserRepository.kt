package ru.cool.sectorsite.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.cool.sectorsite.model.User
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
}