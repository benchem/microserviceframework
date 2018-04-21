package team.benchem.framework.sdk;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class MicroServiceMessageProducer {
    public void publish(String queueName, JSONObject message){
        throw  new NotImplementedException();
    }
}
