package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Child;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
