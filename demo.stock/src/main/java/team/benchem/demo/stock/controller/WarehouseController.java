package team.benchem.demo.stock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/")
public class WarehouseController {
    @RequestMapping("/time")
    public Date getServerTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }


}
