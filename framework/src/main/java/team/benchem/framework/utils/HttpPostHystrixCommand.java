package team.benchem.framework.utils;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.HashMap;

public class HttpPostHystrixCommand extends HystrixCommand<String> {

    private final String host;
    private final String path;
    private final HashMap<String, String> headers;
    private final String requestBody;

    public HttpPostHystrixCommand(String host, String path, HashMap<String, String> headers, String requestBody){
        super(HystrixCommandGroupKey.Factory.asKey("MicroServicesRemoteInvoke"));
        this.host = host;
        this.path = path;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    @Override
    protected String run() throws Exception {
        return HttpInvokeHelper.post(host, path, headers, requestBody);
    }

    @Override
    protected String getFallback() {
        JSONObject json = new JSONObject();
        json.put("statecode", -3);
        json.put("errmsg", "远程服务应答异常");
        return  json.toJSONString();
    }
}