package cn.edu.neu.mgzmsys.entity;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author team15
 * @since 2023-11-09
 */
@Getter
@Setter
public class Volunteer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String password;

    private String name;

    private String sex;

    private String phoneNum;

    private LocalDate birth;

    private String location;

    private String universityName;

    private String description;


}
