package com.example.conf;

import com.example.constant.CacheConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonMap;
@Slf4j
@Configuration
@EnableCaching
public class RedisSentinelConfig {
    @Autowired
    RedisProperties redisProperties;

    //读取pool配置
    @Bean("sentinePoolConfig")
    public GenericObjectPoolConfig sentinePoolConfig() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        config.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        config.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        return config;
    }
    /**
     * @Description:  将哨兵信息放到配置中
     * @date 2020/6/7 14:42
     */
    @Bean("sentinelConfiguration")
    public RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        redisConfig.setMaster(redisProperties.getSentinel().getMaster());
//        redisConfig.setPassword(RedisPassword.of(redisConfigThree.getPassword()));
        if(redisProperties.getSentinel().getNodes()!=null) {
            List<RedisNode> sentinelNode=new ArrayList<RedisNode>();
            for(String sen : redisProperties.getSentinel().getNodes()) {
                String[] arr = sen.split(":");
                sentinelNode.add(new RedisNode(arr[0],Integer.parseInt(arr[1])));
            }
            redisConfig.setSentinels(sentinelNode);
        }
        return redisConfig;
    }
    @Bean("sentinelLettuceConnectionFactory")
    public LettuceConnectionFactory lettuceConnectionFactory(@Qualifier("sentinePoolConfig") GenericObjectPoolConfig config,
                                                             @Qualifier("sentinelConfiguration") RedisSentinelConfiguration redisConfig) {//注意传入的对象名和类型RedisSentinelConfiguration
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(config).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }

    @Bean("sentinelRedisTemplate")
    public RedisTemplate<String, Object> sentinelRedisTemplate(@Qualifier("sentinelLettuceConnectionFactory")LettuceConnectionFactory connectionFactory) {
        log.info(" --- redis config init --- ");
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    @Bean("sentinelCacheManager")
    public CacheManager signCacheManager(@Qualifier("sentinelLettuceConnectionFactory")LettuceConnectionFactory factory){
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
