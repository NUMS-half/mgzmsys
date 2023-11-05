package cn.edu.neu.mgzmsys.mapper;

import cn.edu.neu.mgzmsys.entity.Conversation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */

@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {

    /**
     * 根据两个参与者id查询唯一会话
     */
    Conversation searchByTwoParticipantIds(@Param("participant1Id") String participant1Id, @Param("participant2Id") String participant2Id);
}
