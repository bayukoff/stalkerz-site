package ru.cool.sectorsite.controller

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.model.User
import ru.cool.sectorsite.model.UserRole
import ru.cool.sectorsite.request.BalanceRequest
import ru.cool.sectorsite.response.ChangeEmailResponse
import ru.cool.sectorsite.response.MessageResponse
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.util.ErrorUtil
import ru.cool.sectorsite.validator.UserValidator

@RestController
@RequestMapping("/api/users")
class UserController(
    val userService: UserService) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)
    private val audit = MarkerFactory.getMarker("AUDIT")

    @InitBinder("userDto")
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(UserValidator(userService))
    }

    @GetMapping
    fun users(): List<UserDto>{
        return userService.getAllDto()
    }

    @PostMapping("/new")
    fun create(@RequestBody @Valid userDto: UserDto, bindingResult: BindingResult): ResponseEntity<MessageResponse> {
        if (bindingResult.hasErrors()) {
            val errorsList = bindingResult.fieldErrors
            val errorMessage = StringBuilder()
            errorsList.forEach { error -> errorMessage.appendLine("${error.defaultMessage}") }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse(errorMessage.toString()))
        }
        userDto.role.add(UserRole.ROLE_USER)
        val success = userService.add(User(username = userDto.username, password = userDto.password, email = userDto.email, role = userDto.role, balance = 0))
        if (!success)
            ResponseEntity.badRequest().body(MessageResponse("Ошибка при регистрации"))
        return ResponseEntity.ok(MessageResponse("success"))
    }


    @PatchMapping("/{username}/changeEmail")
    fun changeEmail(
        @PathVariable("username") username: String,
        @RequestBody @Valid userDto: UserDto,
        bindingResult: BindingResult,
        @AuthenticationPrincipal userDetails: MyUserDetails?): ResponseEntity<*>
    {
        val email = userDto.email
        if (userDetails != null) {
            if (userDetails.username != username) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageResponse("Ты не ты..."))
            }
            if (userService.isExistsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(MessageResponse("Пользователь с таким email уже зарегистрирован!"))
            }
            val errorField = ErrorUtil.checkErrorField("email", bindingResult)
            if (errorField != null)
                return ResponseEntity.badRequest().body(mapOf("message" to "Неверно введен email!"))
            userService.updateEmail(userDto.email, username)
            return ResponseEntity.ok(ChangeEmailResponse(email))
        }
        return ResponseEntity.badRequest().body(MessageResponse("Вы не аутентифицированы!"))
    }

    @PatchMapping("/{username}/changePassword")
    fun changePassword(
        @PathVariable("username") username: String,
        @RequestBody @Valid userDto: UserDto,
        bindingResult: BindingResult,
        @AuthenticationPrincipal userDetails: MyUserDetails?
    ): ResponseEntity<*>{
        logger.info("Запрос на изменение пароля игрока {}", username)
        if (userDetails != null) {
            if (userDetails.username != username) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(MessageResponse("Ты не ты..."))
            }
            val errorField = ErrorUtil.checkErrorField("password", bindingResult)
            if (errorField != null)
                return ResponseEntity.badRequest().body(MessageResponse("Пароль не соответсвует требованиям!"))
            userService.updatePassword(userDto.password, username)
            logger.info("Пароль игрока {} был изменен", username)
            return ResponseEntity.ok(HttpStatus.OK)
        }
        return ResponseEntity.badRequest().body(MessageResponse("Вы не аутентифицированы!"))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{username}/balance")
    fun addBalance(@PathVariable("username") username: String, @RequestBody balanceRequest: BalanceRequest): ResponseEntity<*>{
        logger.info("Запрос на добавление баланса игроку {} в количестве {}", username, balanceRequest.balance)
        logger.info(audit, "Запрос на добавление баланса игроку {} отправил админ {}, сумма {}", username, balanceRequest.adminUsername, balanceRequest.balance)
        val user = userService.getByUsername(username)
        if (user != null){
            user.balance += balanceRequest.balance
            userService.persist(user)
            logger.info("Баланс добавлен игроку {}", username)
            return ResponseEntity.ok().body(user.balance)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse("Игрок с таким логином не найден!"))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{username}/balance")
    fun setBalance(@PathVariable("username") username: String, @RequestBody balanceRequest: BalanceRequest): ResponseEntity<*> {
        logger.info("Запрос на изменение баланса на {}", balanceRequest.balance)
        logger.info(audit, "Запрос на изменение баланса игроку {} отправил админ {}, сумма {}", username, balanceRequest.adminUsername, balanceRequest.balance)
        val user = userService.getByUsername(username)
        if (user != null) {
            user.balance = balanceRequest.balance
            userService.persist(user)
            logger.info("Баланс игрока {} изменен", username)
            return ResponseEntity.ok().body(user.balance)
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse("Игрок с таким логином не найден!"))
    }

}