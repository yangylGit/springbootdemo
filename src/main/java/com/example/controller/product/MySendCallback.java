package com.example.controller.product;

import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * # 开发者自定义全局模块
 * ## 自定义实现消息发送结果
 */
@Slf4j
@Component
public class MySendCallback implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("发送消息成功");
    }

    @Override
    public void onException(OnExceptionContext onExceptionContext) {
        log.info("发送消息失败");
    }
}
