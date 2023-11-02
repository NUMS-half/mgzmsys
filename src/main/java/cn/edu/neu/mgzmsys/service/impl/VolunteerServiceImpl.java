package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.mapper.VolunteerMapper;
import cn.edu.neu.mgzmsys.service.IVolunteerService;
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
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements IVolunteerService {

    @Resource
    VolunteerMapper volunteerMapper;

    /**
     * 登录验证业务
     * @return 是否成功
     */
    @Override
    public boolean login(String username, String password) {

        // 通过用户名查询用户
        LambdaQueryWrapper<Volunteer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(username != null, Volunteer::getUsername, username);
        Volunteer volunteer = volunteerMapper.selectOne(lambdaQueryWrapper);

        // 判断用户是否存在，存在则判断密码是否正确
        return volunteer != null && volunteer.getPassword().equals(password);
    }
}
