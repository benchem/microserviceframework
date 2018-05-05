package team.benchem.framework.lang;

public class StateCodeImpl implements StateCode {
    Integer code;

    String message;

    public StateCodeImpl(Integer code, String message) {
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
