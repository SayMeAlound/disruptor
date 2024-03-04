package org.saymeevetime;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.saymeevetime.client.MessageConsumerImpl4Client;
import org.saymeevetime.client.NettyClient;
import org.saymeevetime.disruptor.MessageConsumer;
import org.saymeevetime.disruptor.RingBufferWorkerPoolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisruptorNettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisruptorNettyClientApplication.class, args);


        MessageConsumer[] conusmers = new MessageConsumer[4];
        for (int i = 0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024,
                //new YieldingWaitStrategy(),
                new BlockingWaitStrategy(),
                conusmers);

        //建立连接 并发送消息
        new NettyClient().sendData();

    }

}
