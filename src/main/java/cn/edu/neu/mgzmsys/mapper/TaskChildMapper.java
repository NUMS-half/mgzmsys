package cn.edu.neu.mgzmsys.mapper;

import cn.edu.neu.mgzmsys.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TaskChildMapper  {

    boolean updateChildTask(Map<String, Object> map);
}
