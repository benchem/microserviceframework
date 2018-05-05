package team.benchem.demo.bill.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.framework.annotation.MicroServiceMethodProxy;
import team.benchem.framework.lang.RequestType;

@Service
public class PayServiceProxy {

    @MicroServiceMethodProxy(microserviceKey = "payService", path= "/pay", type= RequestType.POST)
    public boolean payOrder(JSONObject payInfo) {
        throw new NotImplementedException();
    }

    @MicroServiceMethodProxy(microserviceKey = "payService", path= "/query")
    public JSONObject getPayLog(String billNumber){
        throw new NotImplementedException();
    }
}
