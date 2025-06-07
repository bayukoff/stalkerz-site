package ru.cool.sectorsite.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.cool.sectorsite.service.UserService
import ru.cool.sectorsite.util.JwtUtil
import kotlin.math.abs

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userService: UserService
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cookiesToken = request.cookies?.firstOrNull { it.name == "jwt" }?.value
        if (cookiesToken != null){
            val username = jwtUtil.parseToken(cookiesToken)
            val userDetails = userService.loadUserByUsername(username)

            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}