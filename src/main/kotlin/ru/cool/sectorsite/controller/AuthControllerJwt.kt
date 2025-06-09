package ru.cool.sectorsite.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.security.MyUserDetails
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.util.JwtUtil

/**
 * Контроллер использует jwt вместо сессий
 */

@RestController
@RequestMapping("/api/auth")
class AuthControllerJwt(private val userService: UserService, private val jwtUtil: JwtUtil) {
    @PostMapping
    fun authUser(@RequestBody user: UserDto, response: HttpServletResponse): ResponseEntity<*> {
        val userDetails = userService.loadUserByUsername(user.username)
        val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = auth
        val token = jwtUtil.generateToken(userDetails.username)
        response.addCookie(Cookie("jwt", token).apply {
            isHttpOnly = true
            path = "/"
            maxAge = 3600
        })
        return ResponseEntity.ok(mapOf("username" to (auth.principal as MyUserDetails).username))
    }

    @GetMapping("/check")
    fun checkUser(request: HttpServletRequest): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        return if (authentication != null && authentication.isAuthenticated && (authentication.principal as MyUserDetails).username != "anonymousUser") {
            val userDetails = authentication.principal as MyUserDetails
            ResponseEntity.ok(mapOf("username" to userDetails.username))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You're not logged in")
        }
        response.addCookie(Cookie("jwt", "").apply {
            isHttpOnly = true;
            path = "/";
            maxAge = 0
        })
        return ResponseEntity.ok("OK")
    }
}