package ru.cool.sectorsite.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.Temporal
import ru.cool.sectorsite.util.UserRole
import java.time.LocalDateTime

@Table(name = "users")
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(nullable = false, unique = true)
    val username: String = "",
    @Column(nullable = false, unique = true)
    val email: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(name="created_at")
    @Temporal
    val createdAt: LocalDateTime,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: UserRole
){
    constructor(username: String, email: String, password: String, role: UserRole) : this(0, username, email, password, LocalDateTime.now(), role)

    constructor(username: String, password: String) : this(0, username,"", password, LocalDateTime.now(), UserRole.ROLE_USER)
}
