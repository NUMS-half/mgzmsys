package cn.edu.neu.mgzmsys.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * 定义队列名
     */
    private static final String QUEUE = "queue";

    /**
     * 创建队列
     * 1. 配置队列
     * 2. 队列名为 queue
     * 3. true 表示: 持久化 (不填，默认为true,默认持久化)
     * durable： 队列是否持久化。 队列默认是存放到内存中的，rabbitmq 重启则丢失，
     * 若想重启之后还存在则队列要持久化，
     * 保存到 Erlang 自带的 Mnesia 数据库中，当 rabbitmq 重启之后会读取该数据库
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }
}
