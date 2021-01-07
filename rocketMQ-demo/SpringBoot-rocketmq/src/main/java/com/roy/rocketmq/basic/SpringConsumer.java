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
//@Component
//@RocketMQMessageListener(consumerGroup = "MyConsumerGroup", topic = "TestWeixinPay",consumeMode= ConsumeMode.CONCURRENTLY)
public class SpringConsumer implements RocketMQListener<String> {
    @Autowired
    RestTemplate restTemplate;
    @Override
    public void onMessage(String message) {
        System.out.println("Received message : "+ message);
        //调用微信回调查询是否支付成功
        String result ="支付失败";
        Long timeOut=0L;
        for (int i = 1; i <= 300; i++) {
            System.out.println("查询"+i+"次");
            result = restTemplate.getForObject("http://127.0.0.1:8761/frontReg?wwz311="+message, String.class);
            if(result.equals("1")){
                result ="支付成功";
                break;
            }
            timeOut=timeOut+1000;
            try {
                Thread.sleep(timeOut);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
    }

}
