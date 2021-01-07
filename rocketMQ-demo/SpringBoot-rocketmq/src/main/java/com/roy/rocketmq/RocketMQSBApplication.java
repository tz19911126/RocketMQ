package com.roy.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/28
 * @description:
 **/

@SpringBootApplication
public class RocketMQSBApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(RocketMQSBApplication.class,args);
    }
}
