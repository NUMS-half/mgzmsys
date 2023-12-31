package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.component.RabbitMQQueueManager;
import cn.edu.neu.mgzmsys.entity.Conversation;
import cn.edu.neu.mgzmsys.mapper.ConversationMapper;
import cn.edu.neu.mgzmsys.service.IConversationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Resource
    ConversationMapper conversationMapper;

    @Resource
    RabbitMQQueueManager rabbitMQQueueManager;

    /**
     * 建立会话
     *
     * @return 是否建立成功
     */
    @Override
    public boolean setupConversation(Map<String, String> map) {
        LambdaQueryWrapper<Conversation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.and(i -> i.eq(Conversation::getParticipantOneId, map.get("participation1")).eq(Conversation::getParticipantTwoId, map.get("participation2")))
                .or(i -> i.eq(Conversation::getParticipantTwoId, map.get("participation1")).eq(Conversation::getParticipantOneId, map.get("participation2")));
        Conversation conversation = conversationMapper.selectOne(lambdaQueryWrapper);
        if ( conversation != null ) {
            return false;
        }

        conversation = new Conversation();
        conversation.setParticipantOneId(map.get("participation1"));
        conversation.setParticipantTwoId(map.get("participation2"));
        conversationMapper.insert(conversation);

        // 在RabbitMQ中创建以conversationId为name的队列
        rabbitMQQueueManager.createQueueIfNotExists(conversation.getConversationId());

        return true;
    }

    /**
     * 根据两个参与者id获取会话
     */
    @Override
    public Conversation getByTwoParticipantIds(String participantId1, String participantId2) {
        if ( participantId1 == null || participantId2 == null ) {
            return null;
        } else {
            return conversationMapper.searchByTwoParticipantIds(participantId1, participantId2);
        }
    }
    /**
     * 根据会话id获取会话list
     */
    @Override
    public List<Conversation> getByParticipantId(String participantId) {
       LambdaQueryWrapper<Conversation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
       lambdaQueryWrapper.and(i -> i.eq(Conversation::getParticipantOneId, participantId).or().eq(Conversation::getParticipantTwoId, participantId));
         return conversationMapper.selectList(lambdaQueryWrapper);
    }
}
