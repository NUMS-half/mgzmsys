package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;

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
public class Conversation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话id
     */
    @TableId(value = "conversation_id",type= IdType.ASSIGN_UUID)
    private String conversationId;

    /**
     * 参与者1
     */
    private String participantOneId;

    /**
     * 参与者2
     */
    private String participantTwoId;


}
