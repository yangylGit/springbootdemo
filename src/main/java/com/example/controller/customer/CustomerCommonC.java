package com.example.controller.customer;

import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.github.thierrysquirrel.annotation.MessageListener;
import com.github.thierrysquirrel.annotation.RocketListener;
import lombok.extern.slf4j.Slf4j;

/**
 * # 订阅普通、事务、延时、定时消息
 * ## 监听消息使用 messageModel 控制集群或广播消费模式
 */
@Slf4j
@RocketListener(groupID = "GID_commonC",messageModel = PropertyValueConst.BROADCASTING)
public class CustomerCommonC {
    @MessageListener(topic = "commonC",tag = "commonC")
    public boolean commonC(String message) {
        log.info(message);
        return true;
    }
}
