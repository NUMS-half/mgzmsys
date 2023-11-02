package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
      @TableId(value = "message_id",type= IdType.ASSIGN_UUID)
    private String messageId;

    /**
     * 会话id
     */
    private String conversationId;

    /**
     * 收件人id
     */
    private String receiveId;

    /**
     * 发送人id
     */
    private String posterId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息
     */
    private String messageBody;

    /**
     * 时间戳
     */
    private LocalDateTime messageTime;

    /**
     * 消息状态
     */
    private Integer messageStatus;


}
