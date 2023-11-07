package cn.edu.neu.mgzmsys.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class HttpResponseEntity {

    private String code;     //状态码

    private Object data;     //返回数据

    private String message;  //状态消息
     private String token;
      public HttpResponseEntity ok(Object t){
        return new HttpResponseEntity(ResponseEnum.LOGIN_SUCCESS.getCode(), t);
    }

    public static final HttpResponseEntity LOGIN_SUCCESS=
            new HttpResponseEntity(ResponseEnum.LOGIN_SUCCESS.getCode(),
                    ResponseEnum.LOGIN_SUCCESS.getMsg());

    public static final HttpResponseEntity  LOGIN_FAIL=
            new HttpResponseEntity (ResponseEnum.LOGIN_FAIL.getCode(),
                    ResponseEnum.LOGIN_FAIL.getMsg());

    public static final HttpResponseEntity  NO_LOGIN=
            new HttpResponseEntity (ResponseEnum.NO_LOGIN.getCode(),
                    ResponseEnum.NO_LOGIN.getMsg());

    public HttpResponseEntity (String code, Object data) {
        this.code = code;
        this.data = data;
    }
    public HttpResponseEntity (String code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
