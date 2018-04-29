package team.benchem.framework.sdk;


import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import team.benchem.framework.annotation.MicroServiceValidatePermission;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.framework.lang.MicroServiceException;
import team.benchem.framework.lang.SystemStateCode;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@ControllerAdvice
public class MicroServiceRequestAdvisor implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return  true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        Method executeMethod = parameter.getMethod();
        Class<?> executeController =  executeMethod.getDeclaringClass();
        if(executeController.isAnnotationPresent(MicroServiceValidatePermission.class)){
            //todo: 微服务验权
            System.out.println("todo:微服务验权");
        }
        if(executeMethod.isAnnotationPresent(RequestTokenValidate.class)){
            HttpHeaders headers = inputMessage.getHeaders();
            Object tokenObj =  headers.get("Suf-Token");
            if (tokenObj == null){
                throw new MicroServiceException(SystemStateCode.AUTH_ERROR);
            }
            String token = tokenObj.toString();
            if(token == "" || token.length() == 0){
                throw new MicroServiceException(SystemStateCode.AUTH_ERROR);
            }
            //todo: 加入在线 Token 验证代码
            System.out.println("todo: Token 验证代码");
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
