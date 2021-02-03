package com.example.controller.product;

import com.github.thierrysquirrel.annotation.CommonMessage;
import com.github.thierrysquirrel.annotation.RocketMessage;
import com.github.thierrysquirrel.annotation.StartDeliverTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * # 发送延时消息或定时消息
 */
@RestController
@RocketMessage(groupID = "GID_delayed")
public class Delayed {
    //startDeliverTime是时间戳,不能小于当前时间
    @RequestMapping(value = "/delayed")
    @CommonMessage(topic = "delayed", tag = "delayed")
    public String delayed(@StartDeliverTime @RequestParam("startDeliverTime") long startDeliverTime) {
        return "delayed";
    }
}
