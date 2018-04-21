package team.benchem.demo.pay.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.demo.pay.entity.PayLog;
import team.benchem.framework.annotation.MicroServiceValidatePermission;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/")
@MicroServiceValidatePermission
public class AlipayController {
    @RequestMapping("/time")
    public Date getServerTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @RequestMapping(path="/pay", method = RequestMethod.POST)
    public Boolean payOrderBill(@RequestBody JSONObject formData) {
        String billNumber = formData.getString("billNumber");
        Double amount = formData.getDouble("amout");
        return true;
    }

    @RequestMapping("/query")
    public PayLog getPayLog(@RequestParam String billNumber){
        throw  new NotImplementedException();
    }
}
