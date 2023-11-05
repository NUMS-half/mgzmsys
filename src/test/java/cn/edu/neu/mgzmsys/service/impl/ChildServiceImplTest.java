package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.service.IChildService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChildServiceImplTest {
    @Autowired
    IChildService childService;
    @Test
    void selectChildInfo() {
        System.out.println(childService.selectChildInfo("101"));
    }
}