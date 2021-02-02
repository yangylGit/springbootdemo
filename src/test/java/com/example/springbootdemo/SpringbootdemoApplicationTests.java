package com.example.springbootdemo;

import com.example.model.User;
import com.example.service.ICommonService;
import com.example.service.IUserService;
import com.example.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class SpringbootdemoApplicationTests {

    @Autowired
    private ICommonService iCommonService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void contextLoads() {
        User user =iCommonService.getCaheUserById(1054);
        if (null!=user){
            log.info("用户信息："+user.toString());
        }else{
            log.info("-------没有查询到数据---------");
        }
    }
    @Test
    void insertLoads(){
        User user=new User();
        user.setName("A1056");
        user.setPassword("123456");
        user.setAge(20);
        iCommonService.insert(user);
        log.info("用户信息："+user.toString());
    }
    @Test
    void deleteLoads(){
        iCommonService.deleteById(1061);
        log.info("--------删除成功----------");
    }
    @Test
    void updateLoads(){
        User user=new User();
        user.setId(1062);
        user.setName("A1062");
        user.setPassword("123456");
        user.setAge(62);
        iCommonService.updateById(user);
        log.info("--------更新成功----------");
    }
    @Test
    void setRedisTemplate(){
        redisUtil.setEx("sys","yangyilang",10, TimeUnit.SECONDS);
        log.info(redisUtil.get("sys"));
    }
    @Test
    void getUserInfor(){
        User user = iUserService.getById(1054);
        log.info("---------用户信息："+user);
    }
}
