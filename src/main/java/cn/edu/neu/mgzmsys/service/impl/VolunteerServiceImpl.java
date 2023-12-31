package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Volunteer;
import cn.edu.neu.mgzmsys.mapper.VolunteerMapper;
import cn.edu.neu.mgzmsys.service.IVolunteerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-09
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements IVolunteerService {
    @Resource
    VolunteerMapper volunteerMapper;
       /**
     * 查询志愿者信息
     * @return 志愿者信息
     */
    public Volunteer selectVolunteerInfo(String id) {
        QueryWrapper<Volunteer> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "birth", "sex", "description","location","university_name", "phone_num");
        return volunteerMapper.selectOne(wrapper.eq("id", id));
    }
}
