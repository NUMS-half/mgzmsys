package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Child;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class IChildServiceTest {

    @Autowired
    private IChildService childService;

    @Test
    public void login() {

//        // 测试登录
//        boolean result1 = childService.login("zhangsan", "123456");
//        boolean result2 = childService.login("zhangsan", "111111");
//
//        assertTrue(result1);
//        assertFalse(result2);
    }
//      @Test
//      public void selectChildInfo() {
//        System.out.println(childService.selectChildInfo("101"));
//    }
    @Test
    public void updateChildInfo() {
        //定义一个Child对象
        Child child = new Child();
        child.setUserId("101");
        child.setBirthday(LocalDate.of(2000, 1, 1));
        child.setGender(0);
        child.setAddress("北京");
        child.setPhone("123456789");
        child.setHobby("打篮球");
        child.setDescription("我是一个好孩子");
//        //测试更新
        boolean result = childService.updateChildInfo(child);
        assertTrue(result);

    }
}