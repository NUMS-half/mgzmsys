package cn.edu.neu.mgzmsys.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ITaskServiceTest {
    @Autowired
    private ITaskService iTaskService;
    @Test
    void getTaskById() {
    }

    @Test
    void updateTask() {
            Map<String,Object> map=new HashMap<>();
        map.put("task_id","a669104f0b624c6080cd90602233f0da");
        map.put("child_id","101");
        map.put("answer","testanswer2");
        map.put("finish_at",new Date());
        assertTrue( iTaskService.updateTask(map));
    }
}