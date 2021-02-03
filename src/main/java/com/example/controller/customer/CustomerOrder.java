package com.example.controller.customer;

import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.github.thierrysquirrel.annotation.MessageListener;
import com.github.thierrysquirrel.annotation.RocketListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 订阅顺序消息
 */
@Slf4j
@RocketListener(groupID = "GID_order",messageModel = PropertyValueConst.BROADCASTING)
public class CustomerOrder {
    @MessageListener(topic = "order",tag = "order",orderConsumer = true)
    public void sendOneWay(String message) {
        log.info(message);
    }
}
