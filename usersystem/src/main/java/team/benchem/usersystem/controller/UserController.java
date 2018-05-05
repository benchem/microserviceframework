package team.benchem.usersystem.controller;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.usersystem.entity.User;
import team.benchem.usersystem.service.UserService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    //创建用户
    @RequestTokenValidate
    @RequestMapping("/create")
    public User createUser(@RequestBody User userInfo){
        //todo:
        userService.appendUser(userInfo);
        return userInfo;
    }
    //获取用户列表
    @RequestTokenValidate
    @RequestMapping("/list")
    public List<User> findUsers(
            @PathParam("keyword") String keyword,
            @PathParam("page") Integer page,
            @PathParam("size") Integer size
    ){
        return userService.findUsers(keyword, page, size);
    }
    //编辑用户
    @RequestTokenValidate
    @RequestMapping("/modifly")
    public User modifly(@RequestBody JSONObject userInfo){
        String rowId = userInfo.getString("rowId");
        String email = userInfo.getString("email");
        String mobile=userInfo.getString("mobile");

        User modifyUser = new User();
        modifyUser.setRowId(rowId);
        modifyUser.setEmail(email);
        modifyUser.setMobile(mobile);
         return  userService.modifyUser(modifyUser);
    }
    //删除用户
    @RequestTokenValidate
    @RequestMapping("/delete")
    public void deleteUser(@RequestBody String rowId){
        userService.deleteUser(rowId);
    }
    //提升/降级管理员标识
    @RequestTokenValidate
    @RequestMapping("/setadmin")
    public void setAdmin(String rowId,Boolean isAdmin){
        userService.setAdmin(rowId,isAdmin);
    }
    //修改密码
    @RequestTokenValidate
    @RequestMapping("/changepassword")
    public void changepassword(String rowId,String oldPassword,String newPassword){
        userService.changepassword(rowId,oldPassword,newPassword);
    }
    //启用/禁用用户
    @RequestTokenValidate
    @RequestMapping("/setenable")
    public void setEnable(String rowId,Boolean isEnable){
        userService.setEnable(rowId,isEnable);
    }
    //重置密码
    @RequestTokenValidate
    @RequestMapping("/resetpassword")
    public void reSetPassword(String rowId,String newPassword){
        userService.resetPassword(rowId,newPassword);
    }
}
