package ru.cool.sectorsite.service

import ru.cool.sectorsite.model.User

interface IUserCacheWriteService {
    fun add(user: User): Boolean
    fun persist(user: User): Boolean
    fun remove(user: User): Boolean
    fun updateEmail(newEmail: String, username: String): Boolean
    fun updatePassword(newPassword: String, username: String): Boolean
}