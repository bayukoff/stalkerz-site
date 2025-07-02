package ru.cool.sectorsite.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.Temporal
import java.io.Serializable
import java.time.LocalDateTime

@Table(name = "users")
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(nullable = false, unique = true)
    var username: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(name="created_at")
    @Temporal
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name="balance")
    var balance: Int = 0,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column
    @Enumerated(EnumType.STRING)
    val role: MutableSet<UserRole> = mutableSetOf(UserRole.ROLE_USER)
): Serializable{

    constructor(username: String, password: String, email: String) : this(0, username, email, password, LocalDateTime.now(), 0, mutableSetOf(UserRole.ROLE_USER))

    constructor(username: String, password: String, email: String, userRole: MutableSet<UserRole>) : this(username, password, email)

}
