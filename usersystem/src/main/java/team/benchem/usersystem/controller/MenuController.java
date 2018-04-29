package team.benchem.usersystem.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @RequestTokenValidate
    @RequestMapping("/addchannel")
    public Channel appendChannel(@RequestBody Channel channel) {
        return menuService.appendChannel(channel);
    }
}
