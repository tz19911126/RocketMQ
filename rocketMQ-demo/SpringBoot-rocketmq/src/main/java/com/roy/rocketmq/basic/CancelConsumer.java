package com.roy.rocketmq.basic;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 注意下@RocketMQMessageListener这个注解的其他属性
 * @author ：楼兰
 * @date ：Created in 2020/10/22
 * @description:
 **/
@Component
@RocketMQMessageListener(consumerGroup = "cancelOrderGroup", topic = "cancelOrder",consumeMode= ConsumeMode.CONCURRENTLY)
public class CancelConsumer implements RocketMQListener<String> {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public void onMessage(String message) {
        System.out.println("消费时间："+System.currentTimeMillis());
        System.out.println("Received message : "+ message);
    }

}
