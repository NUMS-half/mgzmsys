package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Task;
import cn.edu.neu.mgzmsys.entity.TaskChild;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
public interface ITaskService extends IService<Task> {
    /**
     * 根据儿童id获取任务
     * @return 任务列表
     */
    List<Task> getTaskById(String id);
    /**
     * 更新任务
     * @return 更新是否成功
     */
    boolean updateTask(Map<String, Object> map);

    TaskChild selectTask(Map<String, Object> map);
}
