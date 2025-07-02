package ru.cool.sectorsite.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.cool.sectorsite.security.JwtFilter
import ru.cool.sectorsite.service.UserService

@EnableMethodSecurity
@Configuration
class SecurityConfiguration(val userDetailsService: UserService, val jwtFilter: JwtFilter) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val publicUrls = arrayOf(
            "/", "/registration", "/login", "/news", "/api/**", "/assets/**")
        http
            .cors{}
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests{
                it.requestMatchers("/users/**", "/shop/**").authenticated()
                    .requestMatchers(*publicUrls).permitAll()
            }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
        return auth.authenticationManager
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("http://localhost", "http://85.237.32.17", "http://localhost:3000")
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH")
        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source

    }
}