package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 儿童
 * </p>
 *
 * @author team15
 * @since 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Child extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 儿童姓名
     */
    private String childName;

    /**
     * 儿童性别
     */
    private Integer gender;

    /**
     * 儿童生日
     */
    private LocalDate birthday;

    /**
     * 儿童住址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 儿童爱好
     */
    private String hobby;

    /**
     * 儿童描述
     */
    private String description;


}
