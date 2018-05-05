package team.benchem.framework.lang;

public class Result {
    Integer statecode;
    String msg;
    Object result;

    public Result(Integer statecode, String msg) {
        this.statecode = statecode;
        this.msg = msg;
    }

    public Result(Object result) {
        this.statecode = SystemStateCode.OK.getCode();
        this.msg = SystemStateCode.OK.getMessage();
        this.result = result;
    }

    public Integer getStatecode() {
        return statecode;
    }

    public void setStatecode(Integer statecode) {
        this.statecode = statecode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}