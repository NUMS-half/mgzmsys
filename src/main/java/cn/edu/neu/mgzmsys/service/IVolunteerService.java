package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-09
 */
public interface IVolunteerService extends IService<Volunteer> {
Volunteer selectVolunteerInfo(String id);
}
