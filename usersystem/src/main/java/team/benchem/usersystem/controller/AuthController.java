package team.benchem.usersystem.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.framework.sdk.UserContext;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping("/login")
    public String login(@RequestBody JSONObject formData){
        String userName = formData.getString("username");
        String password = formData.getString("password");
        return authService.login(userName, password);
    }

    @RequestTokenValidate
    @RequestMapping("/logout")
    public void logout(@RequestBody JSONObject formData){
        UserContext ctx = UserContext.getCurrentUserContext();
        String userName = formData.getString("username");
        String token = ctx.properties.getString("Suf-Token");
        authService.logout(userName, token);
    }

    @RequestTokenValidate
    @RequestMapping("/sayonline")
    public String sayOnline(@RequestBody JSONObject formData){
        UserContext ctx = UserContext.getCurrentUserContext();
        String userName = formData.getString("username");
        String token = ctx.properties.getString("Suf-Token");
        return authService.sayOnline(userName, token);
    }

    @RequestTokenValidate
    @RequestMapping("/getmenus")
    public List<Channel> getMenus(){
        //todo: 不在本期实现
        return null;
    }
}
