package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * userID
     */
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
