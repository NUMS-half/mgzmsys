package cn.edu.neu.mgzmsys.entity;

public enum ResponseEnum {
    LOGIN_SUCCESS("200","OK"),
    LOGIN_FAIL("500","NO"),
    NO_LOGIN("501","NO_LOGIN"),
    ;
    private String code;
    private String msg;

    ResponseEnum() {
    }

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}