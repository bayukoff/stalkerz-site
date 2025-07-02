package ru.cool.sectorsite.util

import ru.cool.sectorsite.model.UserRole

data class UserClaim(var username: String, var email: String, var roles: MutableSet<UserRole>, var balance: Int)
