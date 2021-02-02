package com.example.controller;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.annotation.DistributedLock;
import com.example.model.User;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yyl
 * @since 2021-01-23
 */
@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;
    //@Auth
    //@LoginReq
    @DS("slave_1")
    @DistributedLock(fieldIndexs = 1,fieldNames = "id",lockKey = "getUser-lock",expireSeconds = 11)
    @RequestMapping(value = "/user")
   public Map<String,Object> getUser(@RequestParam(name = "id",required = true) int id){
        Map<String,Object> map=new HashMap<>();
        User user = iUserService.getById(id);
        map.put("success",true);
        map.put("data",user);
        return map;
   }
}
