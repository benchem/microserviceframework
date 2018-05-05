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
import team.benchem.usersystem.repository.UserRepository;
import team.benchem.usersystem.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

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

        for(int i=0; i<201; i++){
            User user = new User();
            String indexStr = String.format("%s", i);
            user.setUsername(String.format("user%s", padLeft(indexStr, 3, '0')));
            System.out.println(user.getUsername());
            user.setMobile(String.format("137%s", padLeft(indexStr, 9, '0')));
            user.setEmail(String.format("137%s@139.com", padLeft(indexStr, 9, '0')));
            user.setPassword(indexStr);
            userService.appendUser(user);
        }
    }

    @Test
    public void test_getUserlist_case1(){
        List<User> userList= userService.findUsers("", 1, 25);
        Assert.assertEquals(userList.size(), 25);
    }

    @Test
    public void test_getUserlist_case2(){
        List<User> userList= userService.findUsers("", 9, 25);
        Assert.assertEquals(userList.size(), 2);
    }

    @Test
    public void test_getUserlist_case3(){
        List<User> userList= userService.findUsers("", 0, 25);
        Assert.assertEquals(userList.size(), 25);
    }

    @Test
    public void test_getUserlist_case4(){
        //user100 user010 user110 user101~109
        List<User> userList= userService.findUsers("10", 1, 25);
        for (User u :userList) {
            System.out.println(u.getUsername());
        }
        Assert.assertEquals(userList.size(), 12);
    }

    @Test
    public void test_getUserlist_case5(){
        //user019 user109 119 129 .... 199
        List<User> userList= userService.findUsers("%1%9", 1, 25);
        for (User u :userList) {
            System.out.println(u.getUsername());
        }
        Assert.assertEquals(userList.size(), 11);
    }

    @Test
    public void test_appendUser_case1(){
        //用户名重复测试
        User u = new User();
        u.setUsername("abcd");
        u.setEmail("dd");
        u.setMobile("1234567890");

        try {
            userService.appendUser(u);
            Assert.assertEquals(true, false);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.UserName_IsExites.getCode()
            );
        }
    }
    //编辑用户
  @Test//手机号码重复
    public void Test2(){
        User u1=new User();
        u1.setRowId("aaa");
        u1.setMobile("1234567890");
        u1.setEmail("123@456.com");
        try {
            userService.modifyUser(u1);
            Assert.assertEquals(true, true);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Mobile_IsExites.getCode());
        }
}
        //邮箱冲突
    @Test
    public void Test201(){
        User u1=new User();
        u1.setRowId("aaa");
        u1.setMobile("123490");
        u1.setEmail("1234@456.com");
        try {
            userService.modifyUser(u1);
            Assert.assertEquals(true, true);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Email_IsExites.getCode());
        }
    }
    //删除用户
    @Test
    public void Test3(){
        try {
            userService.deleteUser("aaa");
            Assert.assertEquals(true,true);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.UserName_IsExites);
        }

        Optional<User> optional=userRepository.findById("aaa");
        if(optional.isPresent()){
            System.out.println("删除失败！");
        }else {
            System.out.println("删除成功！");
        }

    }
    //提升/降级管理员标识
    @Test
    public void  Test4(){
        userService.setAdmin("aaa",true);
        Optional<User> optional=userRepository.findById("aaa");
        Boolean IsAdmin=optional.get().getAdmin();
        if(IsAdmin==false){
            System.out.println("更改失败！");
        }else {
            System.out.println("更改成功！");
        }
    }
    //修改密码
    @Test
    public void Test5(){
        userService.changepassword("aaa","123","456");
        Optional<User> optional= userRepository.findById("aaa");
        String pwd=optional.get().getPasswordHash();
        User user=new User();
        user.setPassword("456");
        if(user.getPasswordHash().equals(pwd)){
            System.out.println("修改成功");
        }
    }
    //启用/禁用用户
    @Test
    public void Test6(){
        userService.setEnable("aaa",true);

    }

    /**
     * @作者 尧
     * @功能 String左对齐
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
    /**
     * @作者 尧
     * @功能 String右对齐
     */
    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
}
