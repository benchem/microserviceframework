package team.benchem.framework.sdk;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import team.benchem.framework.bean.MicroServiceException;
import team.benchem.framework.bean.Result;
import team.benchem.framework.bean.StateCode;
import team.benchem.framework.bean.SystemStateCode;

@ControllerAdvice
public class MicroServiceResponseAdvisor implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleUnhandleException(Exception ex, WebRequest request) {
        String errMsg = ex.getMessage();
        if (errMsg != null && !errMsg.isEmpty()) {
            ex.printStackTrace();
        }
        Result result = new Result(SystemStateCode.SYSTEM_ERROR.getCode(), SystemStateCode.SYSTEM_ERROR.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ExceptionHandler(value = MicroServiceException.class)
    public ResponseEntity<Object> handleException(MicroServiceException ex, WebRequest request) {
        StateCode stateCode = ex.getStateCode();
        Result result = new Result(stateCode.getCode(), stateCode.getMessage());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) return body;
        return new Result(body);
    }
}