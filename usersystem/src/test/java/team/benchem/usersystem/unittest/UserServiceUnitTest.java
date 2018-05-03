package team.benchem.usersystem.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import team.benchem.usersystem.entity.User;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.lang.UserSystemStateCode;
import team.benchem.usersystem.service.UserService;

import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    UserService userService;

    @Before
    public  void beforeTest(){
        User u = new User();
        u.setRowId("aaa");
        u.setUsername("abcd");
        u.setEmail("1234@456.com");
        u.setMobile("1234567890");
        u.setAdmin(true);
        u.setEnable(true);
        u.setPassword("123");
        userService.appendUser(u);
    }

    @Test
    public void Test1(){
        User u = new User();
        u.setUsername("abcd");
        u.setEmail("dd");
        u.setMobile("ss");

        try {
            userService.appendUser(u);
            Assert.assertEquals(true, false);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.UserName_IsExites.getCode());
        }
    }
  @Test
    public void Test2(){
        User u1=new User();
        u1.setRowId("aaa");
        u1.setMobile("123@456.com");
        u1.setEmail("123@456.com");
        User uu=userService.modifyUser(u1);
        System.out.println(uu.getRowId());
        System.out.println(uu.getEmail());
        System.out.println(uu.getMobile());
        System.out.println("hello worldaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
}
    //删除用户
    @Test
    public void Test3(){
       userService.deleteUser("aaa");
    }
    //提升/降级管理员标识
    @Test
    public void  Test4(){
        userService.setAdmin("aaa",true);
    }
    //修改密码
    @Test
    public void Test5(){
        userService.changepassword("aaa","123","456");
    }
    //启用/禁用用户
    @Test
    public void Test6(){
        userService.setEnable("aaa",true);
    }
}
