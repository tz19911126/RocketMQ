package com.roy.rocketmq.config;

import com.roy.rocketmq.domain.Wz03Entity;
import com.roy.rocketmq.repo.R01UFDOCIllnessOrderRepo;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：楼兰
 * @date ：Created in 2020/11/5
 * @description: 事务消息监听器
 * 关于@RocketMQTransactionListener 这个注解，有点奇怪。2.0.4版本中，是需要指定txProducerGroup指向一个消息发送者组。不同的组可以有不同的事务消息逻辑。
 * 但是到了2.1.1版本，只能指定rocketMQTemplateBeanMame，也就是说如果你有多个发送者组需要有不同的事务消息逻辑，那就需要定义多个RocketMQTemplate。
 * 而且这个版本中，虽然重现了我们在原生API中的事务消息逻辑，但是测试过程中还是发现一些奇怪的特性，用的时候要注意点。
 **/
//@RocketMQTransactionListener(txProducerGroup = "springBootGroup2")
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class MyTransactionImpl implements RocketMQLocalTransactionListener {
    @Autowired
    R01UFDOCIllnessOrderRepo wz03Repo;
    private ConcurrentHashMap<Object, Message> localTrans = new ConcurrentHashMap<>();
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        Object transId = msg.getHeaders().get(RocketMQHeaders.PREFIX+RocketMQHeaders.TRANSACTION_ID);
        String destination = arg.toString();
        localTrans.put(transId,msg);
        String wwz311 = new String((byte[]) msg.getPayload());

        //这个msg的实现类是GenericMessage，里面实现了toString方法
        //在Header中自定义的RocketMQHeaders.TAGS属性，到这里就没了。但是RocketMQHeaders.TRANSACTION_ID这个属性就还在。
        //而message的Header里面会默认保存RocketMQHeaders里的属性，但是都会加上一个RocketMQHeaders.PREFIX前缀
        System.out.println("executeLocalTransaction msg = "+msg);
        //转成RocketMQ的Message对象
        org.apache.rocketmq.common.message.Message message = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(),"UTF-8",destination, msg);
        String tags = message.getTags();
        message.putUserProperty("CHECK_IMMUNITY_TIME_IN_SECONDS ","30");
        if(StringUtils.contains(tags,"pay-0")){
            System.out.println("检查是否支付");
            Wz03Entity wz03Entity = wz03Repo.findByWwz311(wwz311);
            if(wz03Entity.getWwz307().equals("1")){
                return RocketMQLocalTransactionState.COMMIT;
            }else if(wz03Entity.getWwz307().equals("2")){
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.UNKNOWN;

    }
    //延迟检查的时间间隔要有点奇怪。
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String transId = msg.getHeaders().get(RocketMQHeaders.PREFIX+RocketMQHeaders.TRANSACTION_ID).toString();
        Message originalMessage = localTrans.get(transId);
        //这里能够获取到自定义的transaction_id属性
        System.out.println("checkLocalTransaction msg = "+originalMessage);
        System.out.println("检查时间："+new Date());
        //获取标签时，自定义的RocketMQHeaders.TAGS拿不到，但是框架会封装成一个带RocketMQHeaders.PREFIX的属性
//        String tags = msg.getHeaders().get(RocketMQHeaders.TAGS).toString();
        String tags = msg.getHeaders().get(RocketMQHeaders.PREFIX+RocketMQHeaders.TAGS).toString();
        if(StringUtils.contains(tags,"pay-0")){
            String wwz311 = new String((byte[]) msg.getPayload());
            Wz03Entity wz03Entity = wz03Repo.findByWwz311(wwz311);
            if(wz03Entity.getWwz307().equals("1")){
                System.out.println("订单支付了"+wwz311);
                return RocketMQLocalTransactionState.COMMIT;
            }else if(wz03Entity.getWwz307().equals("2")){
                System.out.println("订单取消了"+wwz311);
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        System.out.println("轮询检查");
         return RocketMQLocalTransactionState.UNKNOWN;

    }
}
