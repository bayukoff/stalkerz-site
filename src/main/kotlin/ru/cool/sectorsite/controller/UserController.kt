package ru.cool.sectorsite.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.validator.UserValidator

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService,
    val userValidator: UserValidator) {
    @GetMapping
    fun users(): List<UserDto>{
        return userService.getAll().map {
            UserDto(it.username, it.email, it.password)
        }
    }

    @PostMapping("/new")
    fun create(@RequestBody @Valid newUserDto: UserDto, bindingResult: BindingResult): ResponseEntity<Map<String, String>> {
        userValidator.validate(newUserDto, bindingResult)
        if (bindingResult.hasErrors()) {
            val errorsList = bindingResult.fieldErrors
            val errorMessage = StringBuilder()
            errorsList.forEach { error -> errorMessage.appendLine("${error.defaultMessage}") }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("message" to errorMessage.toString()))
        }
        userService.save(userService.convertToUser(newUserDto))
        return ResponseEntity.ok(mapOf("message" to "success"))
    }

}