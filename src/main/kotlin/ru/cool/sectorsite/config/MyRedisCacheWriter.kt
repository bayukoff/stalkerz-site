package ru.cool.sectorsite.config

import org.slf4j.LoggerFactory
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

class MyRedisCacheWriter(
    private val delegate: RedisCacheWriter
): RedisCacheWriter by delegate {

    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        fun nonLockingRedisCacheWriter(factory: RedisConnectionFactory): RedisCacheWriter {
            return MyRedisCacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
        }
    }

    override fun get(name: String, key: ByteArray): ByteArray? {
        return try{
            delegate.get(name, key)
        }catch (e: Exception){
            logWarning(e)
            null
        }
    }

    override fun put(name: String, key: ByteArray, value: ByteArray, ttl: Duration?) {
        return try {
            delegate.put(name, key, value, ttl)
        }catch (e: Exception){
            logWarning(e)
        }
    }

    override fun remove(name: String, key: ByteArray) {
        return try {
            delegate.remove(name, key)
        }catch (e: Exception){
            logWarning(e)
        }
    }

    private fun logWarning(e: Exception){
        logger.warn("Ошибка при подключении к Redis {}", e.message)
    }
}