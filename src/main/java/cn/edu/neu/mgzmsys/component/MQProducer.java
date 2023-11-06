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
     * 发送消息到目标会话的queue
     */
    public void sendToChatQueue(Message message) {
        amqpTemplate.convertAndSend(message.getConversationId(), message);
    }
}
