package cn.edu.neu.mgzmsys.entity;

import lombok.Data;

@Data
public class HttpResponseEntity {

    private String code;     //状态码

    private Object data;     //返回数据

    private String message;  //状态消息
}
