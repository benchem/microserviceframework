package team.benchem.framework.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.benchem.framework.annotation.MicroServiceMethodProxy;
import team.benchem.framework.lang.*;
import team.benchem.framework.utils.HttpPostHystrixCommand;
import team.benchem.framework.utils.RsaHelper;

import java.lang.reflect.Type;
import java.util.HashMap;

@Aspect
@Component
public class MicroServiceMethodInvokeAspect {

    Logger logger = LoggerFactory.getLogger(MicroServiceMethodInvokeAspect.class);

    @Value("${microservice.servicecenter}")
    String serviceCenterUrl;

    @Value("${microservice.servicename}")
    String serviceName;

    @Value("${microservice.publickey}")
    String serviceRsaKey;

    @Around(value = "@annotation(microService)")
    public Object aroundMethod(ProceedingJoinPoint point, MicroServiceMethodProxy microService){

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        String[] argsNames = methodSignature.getParameterNames();
        Object[] argsValues = point.getArgs();

        HashMap<String, String> requestHeaders = buildRequestHeaders(
                microService.type(),
                microService.microserviceKey(),
                microService.path()
        );

        String requestBodyStr = "";
        if(microService.type() == RequestType.POST){
            requestBodyStr = argsValues.length == 0 ? "" : JSON.toJSONString(argsValues[0]);
        } else if (microService.type() == RequestType.GET){
            JSONObject queryParam = new JSONObject();
            for (int index=0; index<argsNames.length; index++){
                queryParam.put(argsNames[index], argsValues[index]);
            }
            requestBodyStr = queryParam.toJSONString();
        }

        HttpPostHystrixCommand cmd = new HttpPostHystrixCommand(
                serviceCenterUrl,
                "/invoke",
                requestHeaders,
                requestBodyStr
        );
        String responseStr = cmd.execute();
        JSONObject invokeReuslt = JSONObject.parseObject(responseStr);
        int code = invokeReuslt.getIntValue("statecode");
        if(code == 0) {
            Type targetType = methodSignature.getMethod().getAnnotatedReturnType().getType();
            if(((Class)targetType).isPrimitive()) return invokeReuslt.get("result");
            String jsonStr = invokeReuslt.getJSONObject("result").toJSONString();
            Object reValue = JSON.parseObject(jsonStr, targetType);
            return reValue;
        } else {
            StateCode stateCode = new StateCodeImpl(code, invokeReuslt.getString("errmsg"));
            throw new MicroServiceException(stateCode);
        }
    }

    private HashMap<String, String> buildRequestHeaders(RequestType requestType, String targetServiceName, String targetServicePath){
        HashMap<String, String> headers = new HashMap<>();

        String token ;
        try{
            token = RsaHelper.publicKeyEncrypt(serviceName, serviceRsaKey);
        } catch (Exception ex){
            token = "";
            logger.warn("构建请求头失败：%s", ex.getMessage());
        }

        headers.put("Suf-MS-SourceServiceName", serviceName);
        headers.put("Suf-MS-Token", token);
        headers.put("Suf-MS-TargetServiceName", targetServiceName);
        headers.put("Suf-MS-TargetServicePath", targetServicePath);
        headers.put("Suf-MS-InvokeType", requestType.name());

        return  headers;
    }
}
