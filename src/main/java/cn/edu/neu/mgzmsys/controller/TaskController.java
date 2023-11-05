package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Task;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public HttpResponseEntity getTaskById(@RequestHeader("token")String token) {
        String id = JwtUtil.getUidFromToken(token);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            if ( id == null ) {
                throw new NullPointerException();
            }
            List<Task> taskList = taskService.getTaskById(id);
            httpResponseEntity.setCode("1");
            httpResponseEntity.setData(taskList);
            httpResponseEntity.setMessage("获取任务成功");
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("获取任务时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
    /**
     * 更新任务
     * @return 更新是否成功
     */
    @PostMapping(value = "/updateTask", headers = "Accept=application/json")
    public HttpResponseEntity updateTask(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        map.put("finish_at",new Date());
        try{
            boolean result = taskService.updateTask(map);
            if ( result ) {
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("更新任务成功");
            } else {
                httpResponseEntity.setCode("-1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("更新任务失败");
            }
        } catch ( Exception e ) {
            httpResponseEntity.setCode("-1");
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("更新任务时发生异常，请稍后重试");
        }
        return httpResponseEntity;
    }
}

