package cn.edu.neu.mgzmsys.service;

import cn.edu.neu.mgzmsys.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
    List<Message> selectMessage(String connectionId);

    /**
     * 处理发送的消息
     */
    boolean handleSentMessage(Message message);
}
