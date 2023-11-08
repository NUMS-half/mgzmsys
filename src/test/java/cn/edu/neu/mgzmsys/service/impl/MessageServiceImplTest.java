package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Message;
import cn.edu.neu.mgzmsys.service.IMessageService;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceImplTest {

    @Autowired
    private IMessageService messageService;

    @Test
    public void saveMessage() {

        Message message = new Message();
        message.setConversationId("dfc32fb5ac69b53f0d602f90061eeca9");
        message.setPosterId("101");
        message.setReceiveId("102");
        message.setMessageBody("666");
        message.setMessageTime(LocalDateTime.now());
        message.setMessageType(0);
        message.setMessageStatus(0);

        messageService.saveMessage(message);

        System.out.println(message.getMessageId());
    }
}