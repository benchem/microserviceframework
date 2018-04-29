package team.benchem.framework.sdk;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class UserContext {
    static final HashMap<Long, UserContext> globalUserContexts = new HashMap<>();

    static void appendUserContext(Long threadId, UserContext context){
        globalUserContexts.put(threadId, context);
    }

    static void removeUserContext(Long threadId){
        if(globalUserContexts.containsKey(threadId)){
            globalUserContexts.remove(threadId);
        }
    }

    protected static UserContext createUserContext(){
        Thread currThread = Thread.currentThread();
        Long threadId = currThread.getId();

        UserContext context = new UserContext();
        context.properties.put("threadId", threadId);
        appendUserContext(threadId, context);
        return context;
    }

    protected static void removeCurrentUserContext(){
        Thread currThread = Thread.currentThread();
        Long threadId = currThread.getId();
        removeUserContext(threadId);
    }

    public static UserContext getCurrentUserContext(){
        Thread currThread = Thread.currentThread();
        Long threadId = currThread.getId();
        if(globalUserContexts.containsKey(threadId)){
            return globalUserContexts.get(threadId);
        }
        return null;
    }

    public JSONObject properties = new JSONObject();
}
