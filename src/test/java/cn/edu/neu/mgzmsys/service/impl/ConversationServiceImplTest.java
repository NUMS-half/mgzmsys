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
            put("participation1", "ff09b97d4365292b7a66f36f776555dd");
            put("participation2", "b5bf4b0f994134fc18f50b9fd61c798c");
        }});
    }
}