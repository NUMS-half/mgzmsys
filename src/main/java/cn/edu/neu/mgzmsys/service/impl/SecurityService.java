package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.MyUserDetails;
import cn.edu.neu.mgzmsys.entity.Users;
import cn.edu.neu.mgzmsys.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        Users users =userMapper.selectOne(queryWrapper);
        if(users !=null) {

            //username 数据库产查用户信息
            return new MyUserDetails(users.getUserId(),users.getUserName(),passwordEncoder.encode(users.getPassword()));
        }
        else {
            throw new UsernameNotFoundException("该用户不存在");
        }
    }
}
