package team.benchem.usersystem.lang;

import team.benchem.framework.lang.MicroServiceException;
import team.benchem.framework.lang.StateCode;

public class UserSystemException extends MicroServiceException {

    public UserSystemException(StateCode stateCode) {
        super(stateCode);
    }

    public UserSystemException(StateCode stateCode, String message) {
        super(stateCode, message);
    }

    public UserSystemException(StateCode stateCode, Throwable cause) {
        super(stateCode, cause);
    }

    public UserSystemException(StateCode stateCode, String message, Throwable cause) {
        super(stateCode, message, cause);
    }
}
