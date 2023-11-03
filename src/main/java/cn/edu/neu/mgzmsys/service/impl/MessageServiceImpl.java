package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.entity.Message;
import cn.edu.neu.mgzmsys.mapper.MessageMapper;
import cn.edu.neu.mgzmsys.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Resource
    MessageMapper messageMapper;
    /**
     * 保存消息
     * @return 是否成功
     */
    @Override
    public boolean saveMessage(Map<String,Object> map){
        Message message = new Message();
        message.setConversationId((String) map.get("conversationId"));
        message.setPosterId((String) map.get("posterId"));
        message.setReceiveId((String) map.get("receiveId"));
        message.setMessageBody((String) map.get("messageBody"));
        message.setMessageTime((LocalDateTime) map.get("messageTime"));
        message.setMessageType((Integer) map.get("messageType"));
        return messageMapper.insert(message) > 0;
    }
}
