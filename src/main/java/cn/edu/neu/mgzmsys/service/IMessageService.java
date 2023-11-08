package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Queue;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
public interface IMessageService extends IService<Message> {

    /**
     * 查询消息
     */
    List<Message> selectMessage(String connectionId,String senderId);

    /**
     * 消息队列存储离线发送的消息
     */
    void handleSentMessage(Message message);

    /**
     * 消息队列获取离线发送的消息
     */
    public Queue<Message> getSentMessages(String queueName);

    /**
     * 保存在线发送的消息
     */
    public boolean saveMessage(Message message);
}
