package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Volunteer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 志愿者id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 志愿者姓名
     */
    private String volunteerName;

    /**
     * 志愿者出生日期
     */
    private LocalDate volunteerBirthday;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 描述
     */
    private String description;

    /**
     * 电话
     */
    private String phone;


}
