package org.saymeevetime;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.saymeevetime.disruptor.MessageConsumer;
import org.saymeevetime.disruptor.RingBufferWorkerPoolFactory;
import org.saymeevetime.server.MessageConsumerImpl4Server;
import org.saymeevetime.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisruptorNettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisruptorNettyServerApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[4];
        for(int i =0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024*1024,
                //new YieldingWaitStrategy(),   最占内存  消耗CPU 最高性能策略
                new BlockingWaitStrategy(),
                conusmers);

        new NettyServer();
    }

}
