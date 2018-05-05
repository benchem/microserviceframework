package team.benchem.framework.lang;

public class MicroServiceException extends RuntimeException
{
    private StateCode stateCode;

    public MicroServiceException(StateCode stateCode) {
        this.stateCode = stateCode;
    }

    public MicroServiceException(StateCode stateCode, String message) {
        super(message);
        this.stateCode = stateCode;
    }

    public MicroServiceException(StateCode stateCode, Throwable cause) {
        super(cause);
        this.stateCode = stateCode;
    }

    public MicroServiceException(StateCode stateCode, String message, Throwable cause) {
        super(message, cause);
        this.stateCode = stateCode;
    }

    public StateCode getStateCode() {
        return stateCode;
    }
}
