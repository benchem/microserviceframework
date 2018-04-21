package team.benchem.demo.pay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
