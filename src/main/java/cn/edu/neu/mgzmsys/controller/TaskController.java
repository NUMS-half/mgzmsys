package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Task;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    @Resource
    private cn.edu.neu.mgzmsys.service.ITaskService taskService;
    /**
     * 根据儿童id获取任务
     * @return 任务列表
     */
    @GetMapping(value = "/getTaskById", headers = "Accept=application/json")
    public HttpResponseEntity getTaskById(@RequestHeader("token")String token) throws ParseException {
        String id = JwtUtil.getUidFromToken(token);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            if ( id == null ) {
                throw new NullPointerException();
            }
            List<Task> taskList = taskService.getTaskById(id);
            return httpResponseEntity.get(taskList);
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR;
        }
    }
    /**
     * 更新任务
     * @return 更新是否成功
     */
    @PostMapping(value = "/updateTask", headers = "Accept=application/json")
    public HttpResponseEntity updateTask(@RequestBody Map<String, Object> map) {
        map.put("finish_at",new Date());
        try{
            boolean result = taskService.updateTask(map);
            if ( result ) {
                return HttpResponseEntity.UPDATE_SUCCESS;
            } else {
                return HttpResponseEntity.UPDATE_FAIL;
            }
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR;
        }
    }
}

