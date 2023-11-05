package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
public interface IConversationService extends IService<Conversation> {

    /**
     * 建立会话
     */
    boolean setupConversation(Map<String, String> map);

    /**
     * 根据两个参与者id获取会话
     */
    Conversation getByTwoParticipantIds(String participantId1, String participantId2);
    /**、
     * 根据会话id获取会话list
     */
    List<Conversation> getByParticipantId(String participantId);
}
