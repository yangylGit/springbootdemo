package com.example.controller.product;

import com.github.thierrysquirrel.annotation.CommonMessage;
import com.github.thierrysquirrel.annotation.RocketMessage;
import com.github.thierrysquirrel.core.producer.MessageSendType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * # 发送普通消息三种方式
 */
@RestController
@RocketMessage(groupID = "GID_common")
public class Common {
    @RequestMapping(value = "/commonA")
    @CommonMessage(topic = "commonA", tag = "commonA",messageSendType = MessageSendType.SEND,callback = MySendCallback.class)
    public String sendCommonMsg() {
        return "commonA";
    }
    @RequestMapping(value = "/commonB")
    @CommonMessage(topic = "commonB", tag = "commonB",messageSendType = MessageSendType.SEND_ASYNC,callback = MySendCallback.class)
    public String sendAsyncMsg() {
        return "commonB";
    }
    @RequestMapping(value = "/commonC")
    @CommonMessage(topic = "commonC", tag = "commonC",messageSendType = MessageSendType.SEND_ONE_WAY,callback = MySendCallback.class)
    public String sendOneWayMessage() {
        return "commonC";
    }
}
