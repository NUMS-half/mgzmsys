package cn.edu.neu.mgzmsys.service;

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
public interface IVolunteerService extends IService<Volunteer> {

    /**
     * 查询志愿者信息
     * @return 志愿者信息
     */
    public Volunteer selectVolunteerInfo(String id);
}
