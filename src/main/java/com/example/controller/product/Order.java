package com.example.controller.product;

import com.github.thierrysquirrel.annotation.OrderMessage;
import com.github.thierrysquirrel.annotation.RocketMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * # 发送顺序消息
 */
@RestController
@RocketMessage(groupID = "GID_order")
public class Order {
    @RequestMapping(value = "/order")
    @OrderMessage(topic = "order",tag = "order")
    public String order() {
        return "order";
    }
}
