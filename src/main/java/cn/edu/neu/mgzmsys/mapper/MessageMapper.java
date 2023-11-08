package cn.edu.neu.mgzmsys.mapper;

import cn.edu.neu.mgzmsys.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
