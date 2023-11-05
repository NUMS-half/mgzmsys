package cn.edu.neu.mgzmsys.service.impl;

import cn.edu.neu.mgzmsys.component.MQConsumer;
import cn.edu.neu.mgzmsys.component.MQProducer;
import cn.edu.neu.mgzmsys.entity.Message;
import cn.edu.neu.mgzmsys.mapper.MessageMapper;
import cn.edu.neu.mgzmsys.service.IMessageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
    @Resource
    MessageMapper messageMapper;

    @Resource
    MQProducer mqProducer;

    @Resource
    MQConsumer mqConsumer;

    /**
     * 根据会话id获取消息,按照时间排序
     *
     * @return 消息列表
     */
    @Override
    public List<Message> selectMessage(String connectionId) {
        LambdaQueryWrapper<Message> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //按照message_time时间排序
        lambdaQueryWrapper.eq(Message::getConversationId, connectionId).orderByAsc(Message::getMessageTime);
        return messageMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 处理发送的消息
     */
    @Override
    public boolean handleSentMessage(Message message) {

        // 保存消息到数据库
        if ( messageMapper.insert(message) > 0 ) {
            // 发送消息到消息队列
            mqProducer.sendToChatQueue(message);
            return true;
        } else {
            return false;
        }
    }
}
