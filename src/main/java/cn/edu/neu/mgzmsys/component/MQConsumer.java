package cn.edu.neu.mgzmsys.component;

import cn.edu.neu.mgzmsys.entity.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQConsumer {

    /**
     * 监听chat.queue并返回消息内容
     */
    @RabbitListener(queues = "chat.queue")
    public void listenChatQueue(Message message) {
        System.out.println(message);
    }
}
