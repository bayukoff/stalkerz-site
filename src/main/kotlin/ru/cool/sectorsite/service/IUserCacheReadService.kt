package ru.cool.sectorsite.service

import ru.cool.sectorsite.model.User

interface IUserCacheReadService {
    fun getById(id: Int): User?
    fun getByUsername(username: String): User?
    fun getByEmail(email: String): User?
    fun isExistsByEmail(email: String): Boolean
    fun isExistsByUsername(username: String): Boolean
    fun isExistsById(id: Int): Boolean
}