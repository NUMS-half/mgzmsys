package cn.edu.neu.mgzmsys.component;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitMQQueueManager {

    @Resource
    private AmqpAdmin amqpAdmin;

    /**
     * 创建队列，如果不存在时
     */
    public void createQueueIfNotExists(String conversationId) {
        Queue queue = new Queue(conversationId, true);
        amqpAdmin.declareQueue(queue);
    }
}
