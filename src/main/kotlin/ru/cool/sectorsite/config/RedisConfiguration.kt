package ru.cool.sectorsite.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration

@Configuration
@EnableCaching
class RedisConfiguration {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory = LettuceConnectionFactory("localhost", 6379)

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager{
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .disableCachingNullValues()

        val cacheWriter = MyRedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)

        return RedisCacheManager.builder(cacheWriter).cacheDefaults(configuration).build()
    }
}