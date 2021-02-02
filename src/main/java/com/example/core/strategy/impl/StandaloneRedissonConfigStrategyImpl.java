package com.example.core.strategy.impl;

import com.example.core.strategy.RedissonConfigStrategy;
import com.example.enums.GlobalConstant;
import com.example.prop.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;


/**
 * 单机方式Redisson配置
 *
 * @author zyf
 * @date 2020-11-11
 */
@Slf4j
public class StandaloneRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = GlobalConstant.REDIS_CONNECTION_PREFIX + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            log.info("初始化Redisson单机配置,连接地址:" + address);
        } catch (Exception e) {
            log.error("单机Redisson初始化错误", e);
            e.printStackTrace();
        }
        return config;
    }
}
