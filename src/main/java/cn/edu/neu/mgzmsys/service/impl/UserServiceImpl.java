package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.entity.Users;
import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.mapper.ChildMapper;
import cn.edu.neu.mgzmsys.mapper.UserMapper;
import cn.edu.neu.mgzmsys.mapper.VolunteerMapper;
import cn.edu.neu.mgzmsys.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements IUserService {
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
    public String login(String username, String password) {

        // 通过用户名查询用户
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(username != null, Users::getUserName, username);
        Users users = userMapper.selectOne(lambdaQueryWrapper);
        if (users != null && users.getPassword().equals(password)){
            return users.getUserId();
        }
        // 判断用户是否存在，存在则判断密码是否正确
        return null;
    }
    /**
     * 注册业务
     * @return 是否成功
     */
    @Override
    public boolean register(Child child){
        // 通过用户名查询用户
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(child.getUserName() != null, Users::getUserName, child.getUserName());
        Users users = userMapper.selectOne(lambdaQueryWrapper);
        // 判断用户是否存在，存在则返回false
        if(users != null){
            return false;
        }
        QueryWrapper<Child> wrapper = new QueryWrapper<>();
        //INSERT INTO child ( child_id, child_name, gender, birthday, address, phone, hobby, description) VALUES()

        // 不存在则插入用户
        users = new Users();
        users.setUserName(child.getUserName());
        users.setPassword(child.getPassword());
        userMapper.insert(users);
        child.setUserId(users.getUserId());
        child.setPassword(null);
        child.setUserName(null);
        childMapper.insert(child);
        return true;
    }
    @Override
    public boolean register(Volunteer volunteer){
        // 通过用户名查询用户
        LambdaQueryWrapper<Users> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(volunteer.getName() != null, Users::getUserName, volunteer.getName());
        Users users = userMapper.selectOne(lambdaQueryWrapper);
        // 判断用户是否存在，存在则返回false
        if(users != null){
            return false;
        }
        // 不存在则插入用户
         users = new Users();
        users.setUserName(volunteer.getName());
        users.setPassword(volunteer.getPassword());
        userMapper.insert(users);
        volunteerMapper.insert(volunteer);
        return true;
    }
    /**
     * 修改密码业务
     * @return 是否成功
     */
    @Override
    public boolean updatePassword(String id, String password){
        Users users = userMapper.selectById(id);
        users.setPassword(password);
        return userMapper.updateById(users) > 0;
    }
    /**
     * 查询用户信息
     * @return 用户信息
     */
    //在child表和volunteer表中查找
    @Override
    public Map<String,Object> selectUser(String name){
       QueryWrapper<Child> wrapper = new QueryWrapper<>();
       wrapper.select("user_id", "child_name", "gender", "birthday", "address", "phone", "hobby", "description").eq("child_name",name);
       List<Child> childList= childMapper.selectList(wrapper);
        QueryWrapper<Volunteer> wrapper1 = new QueryWrapper<>();
       wrapper1.select("id", "name", "birth", "sex", "description","location","university_name", "phone_num").eq("name",name);
       List<Volunteer> volunteerList= volunteerMapper.selectList(wrapper1);
       Map<String,Object> map = new HashMap<>();
       map.put("child",childList);
       map.put("volunteer",volunteerList);
         return map;
    }
}
