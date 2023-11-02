package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Child;
import cn.edu.neu.mgzmsys.mapper.ChildMapper;
import cn.edu.neu.mgzmsys.service.IChildService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public Child selectChildInfo(String id) {
        LambdaQueryWrapper<Child> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Child::getUserId, id);
        return childMapper.selectOne(wrapper);
    }
}
