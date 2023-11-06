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
            put("participation1", "101");
            put("participation2", "103");
        }});
    }
}