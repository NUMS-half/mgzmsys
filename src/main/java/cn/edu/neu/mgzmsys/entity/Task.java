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
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 截止时间
     */
    private LocalDateTime deadline;

    /**
     * 任务描述
     */
    private String description;


}
