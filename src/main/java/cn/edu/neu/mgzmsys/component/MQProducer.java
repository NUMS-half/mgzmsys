package cn.edu.neu.mgzmsys.component;

import cn.edu.neu.mgzmsys.entity.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MQProducer {

    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息到test.queue
     */
    public void sendToChatQueue(Message message) {
        amqpTemplate.convertAndSend("chat.queue", message);
    }
}
