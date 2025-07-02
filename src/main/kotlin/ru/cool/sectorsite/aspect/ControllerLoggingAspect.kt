package ru.cool.sectorsite.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import ru.cool.sectorsite.dto.UserDto
import ru.cool.sectorsite.request.BalanceRequest
import ru.cool.sectorsite.security.MyUserDetails

@Aspect
@Component
class ControllerLoggingAspect {

    private val logger = LoggerFactory.getLogger(ControllerLoggingAspect::class.java)
    private val audit = MarkerFactory.getMarker("AUDIT")

    @Around("execution(* ru.cool.sectorsite.controller.UserController.create(..))")
    fun aroundCreateUserAdvice(joinPoint: ProceedingJoinPoint): ResponseEntity<*> {
        val args = joinPoint.args
        val userDto = args[0] as UserDto
        val username = userDto.username
        return successLogging(joinPoint){
            logger.info("Был зарегистрирован пользователь {}", username)
        }
    }

    @Around("execution(* ru.cool.sectorsite.controller.UserController.changeEmail(..))")
    fun aroundChangeEmailAdvice(joinPoint: ProceedingJoinPoint): ResponseEntity<*> {
        val args = joinPoint.args
        val username = args[0] as String
        val userDto = args[1] as UserDto
        val authenticatedUser = args[3] as MyUserDetails
        logger.info("Запрос на изменение email игрока {}", username)
        return successLogging(joinPoint){
            logger.info("Email игрока {} изменен с {} на {}", username, authenticatedUser.user.email, userDto.email)
        }
    }

    @Around("execution(* ru.cool.sectorsite.controller.UserController.changePassword(..))")
    fun aroundChangePasswordAdvice(joinPoint: ProceedingJoinPoint): ResponseEntity<*> {
        val args = joinPoint.args
        val username = args[0] as String
        logger.info("Запрос на изменение пароля игрока {}", username)
        return successLogging(joinPoint){
            logger.info("Проль игрока {} изменен", username)
        }
    }

    @Around("execution(* ru.cool.sectorsite.controller.UserController.addBalance(..))")
    fun aroundAddBalanceAdvice(joinPoint: ProceedingJoinPoint): ResponseEntity<*> {
        val args = joinPoint.args
        val username = args[0] as String
        val balanceRequest = args[1] as BalanceRequest
        logger.info("Запрос на добавление баланса игроку {} в количестве {}", username, balanceRequest.balance)
        logger.info(audit, "Запрос на добавление баланса игроку {} отправил админ {}, сумма {}", username, balanceRequest.adminUsername, balanceRequest.balance)
        return successLogging(joinPoint){
            logger.info("Баланс добавлен игроку {}", username)
            logger.info(audit, "Баланс добавил игроку {} админ {}", username, balanceRequest.adminUsername)
        }
    }

    @Around("execution(* ru.cool.sectorsite.controller.UserController.setBalance(..))")
    fun aroundSetBalanceAdvice(joinPoint: ProceedingJoinPoint): ResponseEntity<*> {
        val args = joinPoint.args
        val username = args[0] as String
        val balanceRequest = args[1] as BalanceRequest
        logger.info("Запрос на изменение баланса игроку {} в количестве {}", username, balanceRequest.balance)
        logger.info(audit, "Запрос на изменение баланса игроку {} отправил админ {}, сумма {}", username, balanceRequest.adminUsername, balanceRequest.balance)
        return successLogging(joinPoint){
            logger.info("Баланс игрока {} изменен на {}", username, balanceRequest.balance)
            logger.info(audit, "Баланс игроку {} изменил админ {}", username, balanceRequest.adminUsername)
        }
    }

    private inline fun successLogging(joinPoint: ProceedingJoinPoint, successLog: () -> Unit): ResponseEntity<*> {
        val result = joinPoint.proceed() as ResponseEntity<*>
        if (result.statusCode.is2xxSuccessful) {
            successLog()
        }
        return result
    }


}