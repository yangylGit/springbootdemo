package com.example.service.impl;

import com.example.constant.CacheConstant;
import com.example.model.User;
import com.example.service.ICommonService;
import com.example.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@CacheConfig(cacheNames = CacheConstant.TEST_DEMO_CACHE)
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private IUserService iUserService;
    /**
     * 通过id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "#id",unless="#result == null")
    public User getCaheUserById(int id) {
        log.info("------------id为："+id+"进入数据库查询--------------");
        return iUserService.getById(id);
    }

    /**
     * 新增用户信息
     *
     * @param user
     * @return
     */
    @Override
    @CachePut(key = "#user.id")
    public User insert(User user) {
        log.info("----------insert------------");
        log.info("----------"+user.toString()+"--------------");
        user.insert();
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    @CacheEvict(key = "#user.id")
    public boolean updateById(User user) {
        log.info("----------updateById------------");
        log.info("----------"+user.toString()+"--------------");
        return user.updateById();
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(key = "#id")
    public boolean deleteById(int id) {
        log.info("----------deleteById------------");
       return iUserService.removeById(id);
    }
}
