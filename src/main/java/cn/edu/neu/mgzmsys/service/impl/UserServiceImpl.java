package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.User;
import cn.edu.neu.mgzmsys.mapper.UserMapper;
import cn.edu.neu.mgzmsys.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Resource
    UserMapper userMapper;
 /**
     * 登录验证业务
     * @return 是否成功
     */
    @Override
    public boolean login(String username, String password) {

        // 通过用户名查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(username != null, User::getUserName, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);

        // 判断用户是否存在，存在则判断密码是否正确
        return user != null && user.getPassword().equals(password);
    }
}
