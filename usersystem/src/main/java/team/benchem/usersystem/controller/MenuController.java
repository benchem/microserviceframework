package team.benchem.usersystem.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.entity.Group;
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

    @RequestTokenValidate
    @RequestMapping("/addgroup")
    public Group appendChannelGroup(@RequestBody JSONObject group){
        String channelId = group.getString("channelId");
        Group groupObj = group.toJavaObject(Group.class);
        return menuService.appendGroup(channelId, groupObj);
    }
}
