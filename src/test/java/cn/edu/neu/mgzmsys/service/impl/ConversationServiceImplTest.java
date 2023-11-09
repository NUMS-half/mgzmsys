package cn.edu.neu.mgzmsys.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ConversationServiceImplTest {

    @Autowired
    ConversationServiceImpl conversationService;

    @Test
    public void setupConversation() {
        conversationService.setupConversation(new HashMap<String, String>() {{
            put("participation1", "a7cbd12084363755c4b5ca9d60d2ed2f");
            put("participation2", "c8c4b70cf4d4541c5124a9cd8b555c5a");
        }});
    }
}