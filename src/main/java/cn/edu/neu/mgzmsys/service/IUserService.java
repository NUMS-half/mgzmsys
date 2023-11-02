package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.User;
import cn.edu.neu.mgzmsys.entity.Volunteer;
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
/**
     * 注册业务
     * @return 是否成功
     */
    boolean register(Child child);
    boolean register(Volunteer volunteer);
}
