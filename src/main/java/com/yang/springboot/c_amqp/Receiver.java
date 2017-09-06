package com.yang.springboot.c_amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Yangjing
 * @explain 消息监听
 */
@Component
public class Receiver {

    //使用＠RabbitListener 来监听RabbitMQ 的目的地发送的消息，通过queues 属性指定要监听的目的地。
    @RabbitListener(queues = "my-queue")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
