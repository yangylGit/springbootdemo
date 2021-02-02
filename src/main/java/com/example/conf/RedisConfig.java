package com.example.conf;

import com.example.constant.CacheConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

import static java.util.Collections.singletonMap;
@Slf4j
//@Configuration
//@EnableCaching
public class RedisConfig{
    /**
     * redisTemplate配置
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
  public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
      log.info(" --- redis config init --- ");
      // 设置序列化
      Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
      ObjectMapper om = new ObjectMapper();
      om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
      om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
      jackson2JsonRedisSerializer.setObjectMapper(om);
      // 配置redisTemplate
      RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
      redisTemplate.setConnectionFactory(lettuceConnectionFactory);
      RedisSerializer<?> stringSerializer = new StringRedisSerializer();
      redisTemplate.setKeySerializer(stringSerializer);// key序列化
      redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
      redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
      redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
      redisTemplate.afterPropertiesSet();
      redisTemplate.setEnableTransactionSupport(true);
      return redisTemplate;
  }
    @Bean
  public CacheManager cacheManager(LettuceConnectionFactory factory){
      // 配置序列化（缓存默认有效期 6小时）
      RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6));
      RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
              .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
      RedisCacheManager cacheManager = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(factory)).cacheDefaults(redisCacheConfiguration)
              .withInitialCacheConfigurations(singletonMap(CacheConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues()))
              .withInitialCacheConfigurations(singletonMap(CacheConstant.PLUGIN_MALL_RANKING, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues()))
              .withInitialCacheConfigurations(singletonMap(CacheConstant.PLUGIN_MALL_PAGE_LIST, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(24)).disableCachingNullValues()))
              .transactionAware().build();
      return cacheManager;
  }
}
