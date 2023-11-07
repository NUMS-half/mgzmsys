package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Task;
import cn.edu.neu.mgzmsys.mapper.TaskChildMapper;
import cn.edu.neu.mgzmsys.mapper.TaskMapper;
import cn.edu.neu.mgzmsys.service.ITaskService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Resource
    TaskMapper taskMapper;
    @Resource
    TaskChildMapper taskChildMapper;

    /**
     * 根据儿童id获取任务
     * @return 任务列表
     */
    @Override
    public List<Task> getTaskById(String id){
        LambdaQueryWrapper<Task> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String sql = "select task_id from child_task where child_id = \"" + id+"\"";
        lambdaQueryWrapper.inSql(Task::getTaskId, sql);
        return taskMapper.selectList(lambdaQueryWrapper);
    }
    /**
     * 更新任务
     * @return 更新是否成功
     */
    @Override
    public boolean updateTask(Map<String, Object> map){
        return taskChildMapper.updateChildTask(map);
    }
}
