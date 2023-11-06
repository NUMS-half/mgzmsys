package cn.edu.neu.mgzmsys;

import cn.edu.neu.mgzmsys.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringAMQPTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSendToTestQueue() {
        String queueName = "chat.queue";
        Message message = new Message();
        message.setConversationId("conservation_id_001");
        message.setPosterId("101");
        message.setReceiveId("102");
        message.setMessageBody("你好");
        message.setMessageTime(null);
        message.setMessageType(1);
        message.setMessageStatus(0);
        amqpTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testReceiveFromTestQueue() {
        String queueName = "chat.queue";
        Message message = (Message) amqpTemplate.receiveAndConvert(queueName);
        System.out.println(message);
    }
}
