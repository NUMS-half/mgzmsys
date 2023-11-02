package cn.edu.neu.mgzmsys.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class IChildServiceTest {

    @Autowired
    private IChildService iChildService;

    @Test
    public void login() {

        // 测试登录
        boolean result1 = iChildService.login("zhangsan", "123456");
        boolean result2 = iChildService.login("zhangsan", "111111");

        assertTrue(result1);
        assertFalse(result2);
    }
}