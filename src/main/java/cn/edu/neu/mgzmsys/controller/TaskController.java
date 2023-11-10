package cn.edu.neu.mgzmsys.controller;


import cn.edu.neu.mgzmsys.common.utils.JwtUtil;
import cn.edu.neu.mgzmsys.entity.HttpResponseEntity;
import cn.edu.neu.mgzmsys.entity.Task;
import cn.edu.neu.mgzmsys.entity.TaskChild;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
    public ResponseEntity<HttpResponseEntity> getTaskById(@RequestHeader("token")String token) throws ParseException {
        String id = JwtUtil.getUidFromToken(token);

        try{
            if ( id == null ) {
                throw new NullPointerException();
            }
            List<Task> taskList = taskService.getTaskById(id);
            return  new HttpResponseEntity().get(taskList).toResponseEntity();
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
    /**
     * 更新任务
     * @return 更新是否成功
     */
    @PostMapping(value = "/updateTask", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> updateTask(@RequestBody Map<String, Object> map,@RequestHeader("token")String token) throws ParseException {
        map.put("finish_at",new Date());
        map.put("child_id",JwtUtil.getUidFromToken(token));
        try{
            boolean result = taskService.updateTask(map);
            if ( result ) {
                return HttpResponseEntity.UPDATE_SUCCESS.toResponseEntity();
            } else {
                return HttpResponseEntity.UPDATE_FAIL.toResponseEntity();
            }
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
    /**
     * 查询任务
     */
    @PostMapping(value = "/taskDetail", headers = "Accept=application/json")
    public ResponseEntity<HttpResponseEntity> selectTask(@RequestBody String taskId,@RequestHeader("token")String token) throws ParseException {
        String id = JwtUtil.getUidFromToken(token);
        try{
            if ( id == null ) {
                throw new NullPointerException();
            }
            Map<String,Object> map = new HashMap<>();
            map.put("child_id",id);
            map.put("task_id",taskId);
            TaskChild result = taskService.selectTask(map);
            return  new HttpResponseEntity().get(result).toResponseEntity();
        } catch ( Exception e ) {
            return HttpResponseEntity.ERROR.toResponseEntity();
        }
    }
}

