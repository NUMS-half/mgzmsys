package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Child;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 儿童 服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
public interface IChildService extends IService<Child> {
    /**
     * 查询儿童信息
     * @return 儿童信息
     */
    Child selectChildInfo(String id);
    /**
     * 更新儿童信息
     * @return 是否成功
     */
    boolean updateChildInfo(Child child);
}
