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
     * 登录验证业务
     * @return 是否成功
     */
    boolean login(String username, String password);
}
