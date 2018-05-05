package team.benchem.framework.lang;

public enum SystemStateCode implements  StateCode {
    OK(0, ""),
    SYSTEM_ERROR(-1, "系统繁忙，请稍候再试"),
    AUTH_ERROR(-2, "无权访问"),
    UNKNOW_REMOTE_REQUESTTYPE(-101, "未知远程调用类型"),
    ;

    private final Integer stateCode;
    private final String message;

    SystemStateCode(Integer stateCode, String message){
        this.stateCode = stateCode;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return stateCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
