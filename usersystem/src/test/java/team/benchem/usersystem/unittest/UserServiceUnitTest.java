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
        User u=new User();
        u.setRowId("u123");
        u.setUsername("admin");
        u.setPassword("123");
        u.setMobile("211314");
        u.setEmail("abc@123.com");
        u.setAdmin(false);
        u.setEnable(false);
        userService.appendUser(u);
        for(int i=0; i<201; i++){
            User user = new User();
            String indexStr = String.format("%s", i);
            user.setUsername(String.format("user%s", padLeft(indexStr, 3, '0')));
            System.out.println(user.getUsername());
            user.setMobile(String.format("137%s", padLeft(indexStr, 8, '0')));
            System.out.println(user.getMobile());
            user.setEmail(String.format("137%s@139.com", padLeft(indexStr, 8, '0')));
            user.setPassword(indexStr);
            user.setAdmin(false);
            user.setEnable(false);
            userService.appendUser(user);
        }
    }
    /**
     *
     * 查询用户列表
    */
    @Test          //关键字为空白字符 页数为1 显示条数为25
    public void test_getUserList_case1(){
        List<User> userList= userService.findUsers("", 1, 25);
        Assert.assertEquals(userList.size(), 25);
    }

    @Test         //关键字为空白字符 页数为9 （最后一页仅两条数据）
    public void test_getUserList_case2(){
        List<User> userList= userService.findUsers("", 9, 25);
        Assert.assertEquals(userList.size(), 2);
    }

    @Test         //关键字为空白字符 页数为0 显示条数为25
    public void test_getUserList_case3(){
        List<User> userList= userService.findUsers("", 0, 25);
        Assert.assertEquals(userList.size(), 25);
    }

    @Test        //关键字为10 页数为1 显示条数为25(实际只有12条)
    public void test_getUserList_case4(){
        List<User> userList= userService.findUsers("10", 1, 25);
        for (User u :userList) {
            System.out.println(u.getUsername());
        }
        Assert.assertEquals(userList.size(), 12);
    }

    @Test        //关键字为%1%9 页数为1 显示条数为25(实际只有11条)
    public void test_getUserList_case5(){
        List<User> userList= userService.findUsers("%1%9", 1, 25);
        for (User u :userList) {
            System.out.println(u.getUsername());
        }
        Assert.assertEquals(userList.size(), 11);
    }

    @Test        //关键字为u %1%9 页数为1 显示条数为25(关键字中有空格)
    public void test_getUserList_case6(){
        List<User> userList= userService.findUsers("u %1%9", 1, 25);
        Assert.assertEquals(userList.size(), 0);
    }

    /**
     *
     * 创建用户
     */
    @Test        //成功创建测试
    public void test_appendUser_case1(){
        User u = new User();
        u.setRowId("2018001");
        u.setUsername("u1");
        u.setPassword("123");
        u.setEmail("2018@01");
        u.setMobile("1001");
        u.setEnable(false);
        u.setAdmin(false);
        User uu=userService.appendUser(u);
        Assert.assertEquals(uu.getRowId(),"2018001");
        Assert.assertEquals(uu.getPasswordHash(),null);
    }

    @Test        //用户名为空白字符
    public void test_appendUser_case2(){
        User u = new User();
        u.setPassword("123");
        u.setEnable(false);
        u.setAdmin(false);
        try {
            userService.appendUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.UserName_IsEmpty.getCode());
        }
    }

    @Test        //密码为空白字符
    public void test_appendUser_case3(){
        User u = new User();
        u.setUsername("u1");
        u.setEnable(false);
        u.setAdmin(false);
        try {
            userService.appendUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.Password_IsEmpty.getCode());
        }
    }

    @Test        //是否管理员为空白字符
    public void test_appendUser_case4(){
        User u = new User();
        u.setUsername("u1");
        u.setPassword("123");
        u.setEnable(false);
        try {
            userService.appendUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.IsAdmin_IsEmpty.getCode());
        }
    }

    @Test        //是否启用为空白字符
    public void test_appendUser_case5(){
        User u = new User();
        u.setUsername("u1");
        u.setPassword("123");
        u.setAdmin(false);
        try {
            userService.appendUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.IsEnable_IsEmpty.getCode());
        }
    }

    @Test        //用户名重复
    public void test_appendUser_case6(){
        User u = new User();
        u.setUsername("user001");
        u.setPassword("123");
        u.setAdmin(false);
        u.setEnable(false);
        try {
            userService.appendUser(u);
            Assert.assertEquals(true, false);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.UserName_IsExites.getCode()
            );
        }
    }

    @Test        //手机号码重复
    public void test_appendUser_case7(){
        User u1=new User();
        u1.setUsername("u1");
        u1.setPassword("123");
        u1.setMobile("13700000200");
        u1.setEmail("123@456.com");
        u1.setAdmin(false);
        u1.setEnable(false);
        try {
            userService.appendUser(u1);
            Assert.assertEquals(true, false);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Mobile_IsExites.getCode());
        }
    }

    @Test        //邮箱冲突
    public void test_appendUser_case8(){
        User u1=new User();
        u1.setUsername("u1");
        u1.setPassword("123");
        u1.setMobile("666666");
        u1.setEmail("13700000200@139.com");
        u1.setAdmin(false);
        u1.setEnable(false);
        try {
            userService.appendUser(u1);
            Assert.assertEquals(true, false);
        }catch(UserSystemException ex ){
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Email_IsExites.getCode());
        }
    }

    /**
    *
    * 编辑用户
    */
    @Test       //编辑成功测试
    public void test_modUser_case1(){
        User u=new User();
        u.setRowId("u123");
        u.setMobile("123344");
        u.setEmail("a@b.com");
        User ret=userService.modifyUser(u);
        System.out.println(ret.toString());
    }

    @Test       //rowId为空白字符
    public void test_modUser_case2(){
        User u=new User();
        u.setRowId("");
        u.setMobile("123344");
        u.setEmail("a@b.com");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //号码为空白字符
    public void test_modUser_case3(){
        User u=new User();
        u.setRowId("u123");
        u.setMobile("");
        u.setEmail("a@b.com");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.Mobile_IsEmpty.getCode());
        }
    }

    @Test       //邮箱为空白字符
    public void test_modUser_case4(){
        User u=new User();
        u.setRowId("u123");
        u.setMobile("123344");
        u.setEmail("");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.Email_IsEmpty.getCode());
        }
    }

    @Test       //用户不存在
    public void test_modUser_case5(){
        User u=new User();
        u.setRowId("u124");
        u.setMobile("123344");
        u.setEmail("a@b.com");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //号码已占用
    public void test_modUser_case6(){
        User u=new User();
        u.setRowId("u123");
        u.setMobile("13700000200");
        u.setEmail("a@b.com");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.Mobile_IsExites.getCode());
        }
    }

    @Test       //邮箱已占用
    public void test_modUser_case7(){
        User u=new User();
        u.setRowId("u123");
        u.setMobile("123344");
        u.setEmail("13700000200@139.com");
        try {
            userService.modifyUser(u);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.Email_IsExites.getCode());
        }
    }

    /**
     *
     * 删除用户
     */
    @Test       //成功删除
    public void test_delUser_case1(){
        userService.deleteUser("u123");
    }

    @Test       //用户id为空白字符
    public void test_delUser_case2(){
        try {
            userService.deleteUser("");
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //用户不存在
    public void test_delUser_case3(){
        try {
            userService.deleteUser("u124");
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    /**
     *
     * 提升/降级管理员标识
     */
    @Test       //操作成功
    public void  test_setAdmin_case1(){
        userService.setAdmin("u123",false);
        Optional<User> optional=userRepository.findById("u123");
        Assert.assertEquals(optional.get().getAdmin(),false);
    }

    @Test       //用户id为空白字符
    public void  test_setAdmin_case2(){
        try {
            userService.setAdmin("      ",false);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //管理员标识为空白字符
    public void  test_setAdmin_case3(){
        try {
            userService.setAdmin("u124",null);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.IsAdmin_IsEmpty.getCode());
        }
    }

    @Test       //用户不存在
    public void  test_setAdmin_case4(){
        try {
            userService.setAdmin("u124",false);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    /**
     *
     * 修改密码
     */
    @Test       //修改成功
    public void test_changePassword_case1(){
        userService.changePassword("u123","123","456");
        Optional<User> optional= userRepository.findById("u123");
        String pwd=optional.get().getPasswordHash();
        User user=new User();
        user.setPassword("456");
        Assert.assertEquals(pwd,user.getPasswordHash());
    }

    @Test       //用户id为空白字符
    public void test_changePassword_case2(){
        try {
            userService.changePassword("","123","456");
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //旧密码为空白字符
    public void test_changePassword_case3(){
        try {
            userService.changePassword("u123", "", "456");
            Assert.assertEquals(true, false);
        } catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.OldPassword_IsEmpty.getCode());
        }
    }

    @Test       //新密码为空白字符
    public void test_changePassword_case4(){
        try {
            userService.changePassword("u123", "123", "");
            Assert.assertEquals(true, false);
        } catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.NewPassword_IsEmpty.getCode());
        }
    }

    @Test       //用户不存在
    public void  test_changePassword_case5(){
        try {
            userService.changePassword("u234","123","456");
            Assert.assertEquals(true, false);
        }catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //旧密码不正确
    public void  test_changePassword_case6(){
        try {
            userService.changePassword("u123","234","456");
            Assert.assertEquals(true, false);
        }catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Password_isErr.getCode());
        }
    }

    @Test       //新密码与旧密码相同
    public void  test_changePassword_case7(){
        try {
            userService.changePassword("u123","123","123");
            Assert.assertEquals(true, false);
        }catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.Password_isReqeat.getCode());
        }
    }

    /**
     *
     * 启用/禁用用户
     */
    @Test       //成功启用
    public void test_setEnable_case1(){
        userService.setEnable("u123",true);
    }

    @Test       //用户id为空白字符
    public void  test_setEnable_case2(){
        try {
            userService.setEnable("     ",false);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //是否启用为空白字符
    public void  test_setEnable_case3(){
        try {
            userService.setEnable("u123",null);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.IsEnable_IsEmpty.getCode());
        }
    }

    @Test       //用户不存在
    public void  test_setEnable_case4(){
        try {
            userService.setEnable("u234",true);
            Assert.assertEquals(true, false);
        }catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.User_IsExites.getCode());
        }
    }

    /**
     *
     * 重置密码
     */
    @Test       //重置密码成功
    public void test_resetPassword_case1(){
        userService.resetPassword("u123","123");
    }

    @Test       //用户id为空白字符
    public void  test_resetPassword_case2(){
        try {
            userService.resetPassword("     ","123");
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.User_IsExites.getCode());
        }
    }

    @Test       //新密码为空白字符
    public void  test_resetPassword_case3(){
        try {
            userService.resetPassword("u123",null);
            Assert.assertEquals(true,false);
        }catch (UserSystemException ex){
            Assert.assertEquals(ex.getStateCode().getCode(),UserSystemStateCode.NewPassword_IsEmpty.getCode());
        }
    }

    @Test       //用户不存在
    public void  test_resetPassword_case4(){
        try {
            userService.resetPassword("u234","123");
            Assert.assertEquals(true, false);
        }catch (UserSystemException ex) {
            Assert.assertEquals(ex.getStateCode().getCode(), UserSystemStateCode.User_IsExites.getCode());
        }
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
