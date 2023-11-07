package cn.edu.neu.mgzmsys.entity;

public enum ResponseEnum {
    LOGIN_SUCCESS("200", "Logged in."),
    LOGIN_FAIL("401", "Invalid credentials."),
    NO_LOGIN("401", "Login required."),
    REGISTER_SUCCESS("201", "Registered."),
    REGISTER_FAIL("400", "Check input."),
    UPDATE_SUCCESS("200", "Updated."),
    UPDATE_FAIL("400", "Update failed."),
    GET_SUCCESS("200", "Success."),
    GET_FAIL("404", "Not found."),
    ERROR("500", "Server error."),
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