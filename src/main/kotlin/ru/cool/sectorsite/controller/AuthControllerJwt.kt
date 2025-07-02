package ru.cool.sectorsite.controller

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.response.AccessTokenResponse
import ru.cool.sectorsite.response.MessageResponse
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.util.JwtUtil
import ru.cool.sectorsite.util.UserClaim
import ru.cool.sectorsite.util.exception.UserNotFoundException

/**
 * Контроллер использует jwt вместо сессий
 */

@RestController
@RequestMapping("/api/auth")
class AuthControllerJwt(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager)
{
    @PostMapping
    fun authUser(@RequestBody user: UserDto, response: HttpServletResponse): ResponseEntity<*> {
        try {
            val userDetails = userService.loadUserByUsername(user.username)
            val authToken = UsernamePasswordAuthenticationToken(userDetails, user.password, userDetails.authorities)
            val dbUser = userService.getByUsername(user.username)
            lateinit var refreshToken: String
            lateinit var accessToken: String
            try {
                if (dbUser != null) {
                    val auth = authenticationManager.authenticate(authToken)
                    SecurityContextHolder.getContext().authentication = auth
                    refreshToken = jwtUtil.generateToken(userDetails.username)
                    accessToken = jwtUtil.generateAccessToken(refreshToken, UserClaim(dbUser.username, dbUser.email, dbUser.role, dbUser.balance))
                    response.addCookie(Cookie("refresh", refreshToken).apply {
                        isHttpOnly = true
                        path = "/"
                        maxAge = 3600
                    })
                }
            }catch (e: BadCredentialsException){
                return ResponseEntity.badRequest().body(MessageResponse("Неверный пароль!"))
            }
            return ResponseEntity.ok(AccessTokenResponse(accessToken)) //токен
        }catch(e: UserNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageResponse("Пользователь не найден!"))
        }
    }

    @GetMapping("/refresh")
    fun refreshToken(@CookieValue("refresh") refreshToken: String?): ResponseEntity<*> {
        lateinit var accessToken: String
        if (refreshToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageResponse("Отсуствует refresh токен"))
        try {
            val username = jwtUtil.validateToken(refreshToken)
            val user = userService.getByUsername(username)
            if (user != null) {
                accessToken = jwtUtil.generateAccessToken(refreshToken, UserClaim(user.username, user.email, user.role, user.balance))
            }
        } catch (e: TokenExpiredException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Int>()
        } catch (e: JWTVerificationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Int>()
        }
        return ResponseEntity.ok(AccessTokenResponse(accessToken))
    }


    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") accessToken: String?, response: HttpServletResponse, @AuthenticationPrincipal userDetails: MyUserDetails?): ResponseEntity<MessageResponse> {
        if (accessToken != null){
            response.addCookie(Cookie("refresh", "").apply {
                isHttpOnly = true
                maxAge = 0
                path = "/"
            })
            return ResponseEntity.ok(MessageResponse("OK"))
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(MessageResponse("incorrect access token"))
    }
}