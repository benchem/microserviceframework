package team.benchem.usersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.benchem.framework.annotation.RequestTokenValidate;
import team.benchem.usersystem.entity.User;
import team.benchem.usersystem.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestTokenValidate
    @RequestMapping("/create")
    public User createUser(@RequestBody User userInfo){
        //todo:
        return userInfo;
    }
}
