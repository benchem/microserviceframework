package team.benchem.demo.bill.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.demo.bill.entity.OrderBill;
import team.benchem.demo.bill.service.OrderService;

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

    @RequestMapping(path="/create", method = RequestMethod.POST)
    public String createOrder(@RequestBody JSONObject formData){
        throw  new NotImplementedException();
    }

    @RequestMapping(path="/pay", method = RequestMethod.POST)
    public String payOrderBill(@RequestBody JSONObject formData){
        throw  new NotImplementedException();
    }

    @RequestMapping("/query")
    public OrderBill queryOrderBill(@RequestParam String billNumber){
        throw  new NotImplementedException();
    }
}
