package com.codingShuttle.Ashish.Caching_Concurrency.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    // We can even modify the cache manager.
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        GenericJacksonJsonRedisSerializer serializer  = GenericJacksonJsonRedisSerializer.builder()
                .enableDefaultTyping( // telling Jackson which Java classes it is allowed to recreate from the JSON.
                        BasicPolymorphicTypeValidator.builder() // This is for security. If we do not use this, then we are allowing all types of info.
                                .allowIfSubType(
                                        "com.codingShuttle.Ashish.Caching_Concurrency"
                                )
                                .build()
                )
                .build();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60)) // After this time the entry will delete from the cache.
                .enableTimeToIdle() // This means if someone is using this data then don't delete it from the cache, even restart it's TTL.
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // It serializes/convert the keys to normal string.
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)); // It serializes the values to JSON for better security rather than normal byte string.

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

}

