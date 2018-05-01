package team.benchem.usersystem.lang;

import team.benchem.framework.lang.StateCode;

public enum UserSystemStateCode implements StateCode {
    Channel_IsExist(200001, "频道菜单已存在")
    ;

    private Integer code;
    private String message;

    UserSystemStateCode(Integer code, String message){

        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
