package com.roy.rocketmq.controller;

import com.roy.rocketmq.basic.SpringProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author ：楼兰
 * @date ：Created in 2020/10/27
 * @description:
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${mqTopic.TestWeixinPay}")
    private String TestWeixinPay;
    @Value("${mqTopic.cancelOrder}")
    private String cancelOrder;

    private final String topic = "TestTopic";
    @Resource
    private SpringProducer producer;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/send")
    public String sendMessage(String message){
        restTemplate.getForObject("http://localhost:8099/MQTest/sendTransactionMessage?message="+message, String.class);

        return "消息发送完成";
    }

    @RequestMapping("/cancelOrder")
    public String cancleOrder(String message){
        producer.cancelOrder(cancelOrder,message);
        return "消息发送完成";
    }
    //这个发送事务消息的例子中有很多问题，需要注意下。
    @RequestMapping("/sendTransactionMessage")
    public String sendTransactionMessage(String message) throws InterruptedException {
        producer.sendMessageInTransaction(topic,message);
        return "消息发送完成";
    }
}