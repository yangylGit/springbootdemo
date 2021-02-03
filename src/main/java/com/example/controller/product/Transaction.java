package com.example.controller.product;

import com.github.thierrysquirrel.annotation.RocketMessage;
import com.github.thierrysquirrel.annotation.TransactionMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * # 发送事务消息
 */
@RestController
@RocketMessage(groupID = "GID_transaction")
public class Transaction {
    @RequestMapping(value = "/transaction")
    @TransactionMessage(topic = "transaction",tag = "transaction")
    public String transaction() {
        return "transaction";
    }
}
