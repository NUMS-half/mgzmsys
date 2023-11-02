package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.User;
import cn.edu.neu.mgzmsys.mapper.UserMapper;
import cn.edu.neu.mgzmsys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
