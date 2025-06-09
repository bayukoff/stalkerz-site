package ru.cool.sectorsite.util.exception

import java.lang.Exception

class UserNotFoundException: Exception("User not found in db!") {
}