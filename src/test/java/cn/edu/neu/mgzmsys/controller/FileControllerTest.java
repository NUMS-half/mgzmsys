package cn.edu.neu.mgzmsys.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", "text/plain", "Hello, World!".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
                        .file(file)
                        .param("messageId", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDownloadFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/file/download/hello.txt"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
