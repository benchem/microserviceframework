package team.benchem.demo.bill.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.demo.bill.entity.OrderBill;
import team.benchem.demo.bill.repository.OrderBillRepository;
import team.benchem.framework.annotation.MicroServiceMessageConsumer;

@Service
public class OrderService {

    @Autowired
    PayServiceProxy payService;

    @Autowired
    OrderBillRepository billRepository;

    public String createOrderBill(String productName, Double amount){
        throw new NotImplementedException();
    }

    public Boolean payOrderBill(String billNumber, Double amount){
        throw new NotImplementedException();
    }

    public OrderBill getOrderBill(String billNumber){
        return billRepository.findByBillNumber(billNumber);
    }

    @MicroServiceMessageConsumer(queueName = "team.benchem.demo.pay")
    public void payStateChanged(JSONObject sender){

    }

    @MicroServiceMessageConsumer(queueName = "team.benchem.demo.stock")
    public void stockStateChanged(JSONObject sender){

    }
}
