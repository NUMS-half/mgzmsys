package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private String messageID;

    /**
     * 会话id
     */
    private String conversationID;

    /**
     * 收件人id
     */
    private String receiveID;

    /**
     * 发送人id
     */
    private String posterID;

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
