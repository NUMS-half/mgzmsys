package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.mapper.ChildMapper;
import cn.edu.neu.mgzmsys.service.IChildService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 儿童 服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class ChildServiceImpl extends ServiceImpl<ChildMapper, Child> implements IChildService {

    @Resource
    ChildMapper childMapper;

 /**
     * 查询儿童信息
     * @return 儿童信息
     */
    @Override
    public Child selectChildInfo(String id) {
    QueryWrapper<Child> queryWrapper = new QueryWrapper<>();
    queryWrapper.select("child_id", "child_name", "gender", "birthday", "address", "phone", "hobby", "description");
    return  childMapper.selectOne(queryWrapper.eq("child_id", id));

    }
    /**
     * 更新儿童信息
     * @return 是否成功
     */
    @Override
    public boolean updateChildInfo(Child child) {
        QueryWrapper<Child> wrapper = new QueryWrapper<>();
        //生成update child_name,gender,birthday,address,phone,hobby,description from child where child_id = #{userId}的wrapper
        wrapper.eq("child_id", child.getUserId());

        return childMapper.update(child, wrapper) == 1;
    }
}
