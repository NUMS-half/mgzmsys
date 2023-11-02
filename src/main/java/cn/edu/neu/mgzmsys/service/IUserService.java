package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
public interface IUserService extends IService<User> {
boolean login(String username, String password);
}
