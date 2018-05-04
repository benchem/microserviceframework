package team.benchem.usersystem.lang;

import sun.security.util.Password;
import team.benchem.framework.lang.StateCode;

public enum UserSystemStateCode implements StateCode {
    Channel_IsExist(200001, "频道菜单已存在"),
    Channel_IsNotExist(200002, "频道菜单不存在"),
    Group_IsExist(200003, "频道菜单下分组已存在"),
    Group_IsNotExist(200004, "频道菜单下分组不存在"),
    UserName_IsEmpty(200040,"用户名不能为空"),
    Email_IsEmpty(200041,"邮箱不能为空"),
    Mobile_IsEmpty(200042,"手机号码不能为空"),
    UserName_IsExites(200050, "用户名已存在"),
    Email_IsExites(200051,"此邮箱已注册"),
    Mobile_IsExites(200052,"此号码已占用"),
    User_IsExites(200053,"用户不存在"),
    Password_isErr(200054,"旧密码输入不正确"),
    Password_isReqeat(200054,"新密码不能与旧密码相同"),
    Password_IsEmpty(200055,"密码不能为空")
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
