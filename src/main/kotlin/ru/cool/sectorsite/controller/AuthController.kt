package ru.cool.sectorsite.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.security.MyUserDetails

/**
 * Контроллер использует аутентификацию через сессии. Вместо него будет использоваться AuthControllerJwt
 */

//@RestController
//@RequestMapping("/api/auth")
class AuthController(
//    val authenticationManager: AuthenticationManager
) {

//    @PostMapping
//    fun authUser(@RequestBody user: UserDto, request: HttpServletRequest): ResponseEntity<*> {
//        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user.username, user.password)
//        val authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
//        SecurityContextHolder.getContext().authentication = authentication
//
//        request.session.setAttribute(
//            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//            SecurityContextHolder.getContext()
//        )
//
//        return ResponseEntity.ok(mapOf("username" to authentication.name) )
//    }
//
//    @GetMapping("/check")
//    fun checkUser(): ResponseEntity<*> {
//        val authentication = SecurityContextHolder.getContext().authentication
//        if (authentication != null && authentication.isAuthenticated && authentication.principal is MyUserDetails) {
//            val username = authentication.name
//            return ResponseEntity.ok(mapOf("username" to username))
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED)
//    }
//
//    @PostMapping("/logout")
//    fun logout(request: HttpServletRequest): ResponseEntity<*> {
//        val authentication = SecurityContextHolder.getContext().authentication
//        if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You're not logged in")
//        }
//
//        SecurityContextHolder.clearContext()
//        request.session.invalidate()
//        return ResponseEntity.ok("OK")
//    }
}