package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.User;
import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.mapper.ChildMapper;
import cn.edu.neu.mgzmsys.mapper.UserMapper;
import cn.edu.neu.mgzmsys.mapper.VolunteerMapper;
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
    @Resource
    ChildMapper childMapper;
    @Resource
    VolunteerMapper volunteerMapper;
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
    /**
     * 注册业务
     * @return 是否成功
     */
    @Override
    public boolean register(Child child){
        // 通过用户名查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(child.getUserName() != null, User::getUserName, child.getUserName());
        User user = userMapper.selectOne(lambdaQueryWrapper);
        // 判断用户是否存在，存在则返回false
        if(user != null){
            return false;
        }
        // 不存在则插入用户
        user = new User();
        user.setUserName(child.getUserName());
        user.setPassword(child.getPassword());
        userMapper.insert(user);
        childMapper.insert(child);
        return true;
    }
    @Override
    public boolean register(Volunteer volunteer){
        // 通过用户名查询用户
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(volunteer.getUserName() != null, User::getUserName, volunteer.getUserName());
        User user = userMapper.selectOne(lambdaQueryWrapper);
        // 判断用户是否存在，存在则返回false
        if(user != null){
            return false;
        }
        // 不存在则插入用户
         user = new User();
        user.setUserName(volunteer.getUserName());
        user.setPassword(volunteer.getPassword());
        userMapper.insert(user);
        volunteerMapper.insert(volunteer);
        return true;
    }
}
