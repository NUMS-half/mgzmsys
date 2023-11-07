package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Volunteer extends Users implements Serializable {

    private static final long serialVersionUID = 1L;

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
