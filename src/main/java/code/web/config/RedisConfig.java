package code.web.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.databind.json.JsonMapper;

/**
 * Redis-backed cache. A Redis outage only affects cache hits; persistence
 * remains JPA-backed.
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisConfig implements org.springframework.cache.annotation.CachingConfigurer {
    @Bean
    RedisSerializer<Object> redisSerializer(
            @Value("${app.redis.value-format:JSON}") String valueFormat) {
        RedisSerializer<Object> json = new GenericJacksonJsonRedisSerializer(JsonMapper.builder().build());
        return "BASE64".equalsIgnoreCase(valueFormat) ? new Base64RedisSerializer(json) : json;
    }

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(
            RedisSerializer<Object> redisSerializer,
            @Value("${app.redis.cache-key-prefix}") String cacheKeyPrefix) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues()
                .prefixCacheNameWith(cacheKeyPrefix)
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));
    }

    @Bean
    RedisCacheManager cacheManager(
            RedisConnectionFactory connectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    /**
     * Redis is a cache, not a persistence dependency: requests continue on an
     * outage.
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException ex, Cache cache, Object key) {
            }

            @Override
            public void handleCachePutError(RuntimeException ex, Cache cache, Object key, Object value) {
            }

            @Override
            public void handleCacheEvictError(RuntimeException ex, Cache cache, Object key) {
            }

            @Override
            public void handleCacheClearError(RuntimeException ex, Cache cache) {
            }
        };
    }
}
