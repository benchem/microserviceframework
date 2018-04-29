package team.benchem.demo.bill.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.demo.bill.entity.OrderBill;
import team.benchem.demo.bill.service.OrderService;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.framework.sdk.UserContext;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/")
public class OrderBillController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/time")
    public Date getServerTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @RequestTokenValidate
    @RequestMapping(path="/create", method = RequestMethod.POST)
    public String createOrder(@RequestBody OrderBill orderBill){
        UserContext ctx = UserContext.getCurrentUserContext();
        System.out.println(ctx.properties.getString("Suf-Token"));
        return "OK";
    }

    @RequestTokenValidate
    @RequestMapping(path="/pay", method = RequestMethod.POST)
    public String payOrderBill(@RequestBody JSONObject formData){
        throw  new NotImplementedException();
    }

    @RequestTokenValidate
    @RequestMapping("/query")
    public OrderBill queryOrderBill(@RequestParam String billNumber){
        throw  new NotImplementedException();
    }

}
