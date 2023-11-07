package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class Users implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)//防止json传过来导致精度缺失

    private static final long serialVersionUID = 1L;

    /**
     * userID
     */
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;




}
