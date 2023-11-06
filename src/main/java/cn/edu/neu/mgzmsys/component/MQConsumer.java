package cn.edu.neu.mgzmsys.component;

import cn.edu.neu.mgzmsys.entity.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.PriorityQueue;
import java.util.Queue;

@Component
public class MQConsumer {

    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 接收目标会话queue中的消息,并以队列形式返回
     */
    public Queue<Message> getMessagesFromQueue(String queueName) {
        // 创建队列
        Queue<Message> messageQueue = new PriorityQueue<>();
        // 循环接收队列中的消息，直到队列为空
        Message message = (Message) amqpTemplate.receiveAndConvert(queueName);
        while ( message != null ) {
            messageQueue.add(message);
            message = (Message) amqpTemplate.receiveAndConvert(queueName);
        }
        // 返回队列
        return messageQueue;
    }
}
