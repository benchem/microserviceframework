package team.benchem.framework.utils;


import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.util.StringPool;

import java.util.HashMap;
import java.util.Map;

public class HttpInvokeHelper {

    public static String get(String host, String path, HashMap<String, String> headers, HashMap<String, Object> queryParam){
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> item : queryParam.entrySet()) {
            params.put(item.getKey(), item.getValue().toString());
        }
        String url = pathUrl(host, path);
        HttpRequest request = HttpRequest.get(url);
        if(headers != null && headers.size() > 0){
            request.header(headers);
        }
        if(queryParam != null){
            request.query(params);
        }

        HttpResponse response = request.send();
        return response.bodyText();
    }

    public static String post(String host, String uri, HashMap<String, String> headers, String postData){
        String url = pathUrl(host, uri);
        HttpRequest request = HttpRequest.post(url);
        request.contentType("application/json", StringPool.UTF_8);
        if(headers != null && headers.size() > 0){
            request.header(headers);
        }
        if(postData != null && postData.length() > 0){
            request.body(postData);
        }
        HttpResponse response = request.send();
        return response.bodyText();
    }

    private static String pathUrl(String host, String path){
        if(!host.endsWith("/") && !path.startsWith("/")){
            return String.format("%s/%s", host, path);
        }
        return String.format("%s%s", host, path);
    }
}
