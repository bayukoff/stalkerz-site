package ru.cool.sectorsite.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtUtil {

    val secretKey: String = System.getenv("SECRET_KEY") ?: "aboba"

    fun generateToken(username: String): String{
        return JWT.create()
            .withSubject("Authentication")
            .withClaim("username", username)
            .withIssuedAt(Date.from(Instant.now()))
            .withIssuer("stalkerz")
            .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(60).toInstant()))
            .sign(Algorithm.HMAC256(secretKey))
    }

    fun parseToken(token: String): String{
        val verifier = JWT.require(Algorithm.HMAC256(secretKey))
            .withSubject("Authentication")
            .withIssuer("stalkerz")
            .build()
        return verifier.verify(token).getClaim("username").asString()
    }
}