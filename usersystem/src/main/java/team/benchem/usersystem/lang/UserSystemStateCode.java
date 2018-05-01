package team.benchem.usersystem.lang;

import team.benchem.framework.lang.StateCode;

public enum UserSystemStateCode implements StateCode {
    Channel_IsExist(200001, "频道菜单已存在"),
    Channel_IsNotExist(200002, "频道菜单不存在"),
    Group_IsExist(200003, "频道菜单下分组已存在"),
    Group_IsNotExist(200004, "频道菜单下分组不存在")
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
