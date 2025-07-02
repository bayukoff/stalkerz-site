package ru.cool.sectorsite.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.cool.sectorsite.model.User
import java.util.*

class MyUserDetails(val user: User): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user.role.map { SimpleGrantedAuthority(it.name) }.toMutableList()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username
}