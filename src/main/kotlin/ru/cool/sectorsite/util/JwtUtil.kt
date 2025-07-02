package ru.cool.sectorsite.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtUtil {

    val secretKey: String = System.getenv("SECRET_KEY") ?: "aboba"

    fun generateToken(username: String): String{
        return JWT.create()
            .withSubject(username)
            .withIssuedAt(Date.from(Instant.now()))
            .withIssuer("stalkerz")
            .withExpiresAt(Date.from(ZonedDateTime.now().plusDays(5).toInstant()))
            .sign(Algorithm.HMAC256(secretKey))
    }

    fun validateToken(token: String): String{
        val verifier = JWT.require(Algorithm.HMAC256(secretKey))
            .withIssuer("stalkerz")
            .build()
        return verifier.verify(token).subject
    }

    fun generateAccessToken(refreshToken: String, userClaim: UserClaim): String{
        val subject = validateToken(refreshToken)
        return JWT.create()
            .withSubject(subject)
            .withClaim("username", userClaim.username)
            .withClaim("email", userClaim.email)
            .withClaim("roles", userClaim.roles.toMutableList().map { it.name })
            .withClaim("balance", userClaim.balance)
            .withIssuer("stalkerz")
            .withIssuedAt(Date.from(Instant.now()))
            .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(15).toInstant()))
            .sign(Algorithm.HMAC256(secretKey))
    }
}