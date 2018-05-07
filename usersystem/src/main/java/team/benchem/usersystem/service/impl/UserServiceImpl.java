package team.benchem.usersystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import team.benchem.framework.lang.MicroServiceException;
import team.benchem.usersystem.entity.User;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.lang.UserSystemStateCode;
import team.benchem.usersystem.repository.UserRepository;
import team.benchem.usersystem.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackOn = {MicroServiceException.class, RuntimeException.class})
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    //根据关键字查询用户分页列表
    @Override
    public  List<User> findUsers(String keyword, Integer page, Integer size){
        Integer queryPage = page <= 0 ? 1 : page;
        Integer querySize = size <= 0 ? 25 : size;
        PageRequest pageable = new PageRequest(queryPage -1, querySize);
        String queryKeywork = keyword== null || keyword.replaceAll("\\s*","").equals("") ? "%" : keyword;
        if(!queryKeywork.contains("%")){
            queryKeywork = "%" + queryKeywork + "%";
        }
        //不能返回密码 Todo
        return userRepository.findAllByUsernameLikeOrderByUsername(queryKeywork, pageable);
    }
    //添加用户
    @Override
    public User appendUser(User user) {
        //判断用户名，密码，是否管理员，是否启用是否为空
        if(user.getUsername()==null||user.getUsername().replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.UserName_IsEmpty);
        }
        if(user.getPasswordHash()==null||user.getPasswordHash().replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.Password_IsEmpty);
        }
        if(user.getAdmin()==null){
            throw new UserSystemException(UserSystemStateCode.IsAdmin_IsEmpty);
        }
        if(user.getEnable()==null){
            throw new UserSystemException(UserSystemStateCode.IsEnable_IsEmpty);
        }
        //判断用户名，邮箱，手机号码是否已注册
        Optional<User> chk1=userRepository.findByUsername(user.getUsername());
        Optional<User> chk2=userRepository.findByMobile(user.getMobile());
        Optional<User> chk3=userRepository.findByEmail(user.getEmail());
        if(chk1.isPresent()){
            throw new UserSystemException(UserSystemStateCode.UserName_IsExites);
        }
        if(chk2.isPresent()){
            throw new UserSystemException(UserSystemStateCode.Mobile_IsExites);
        }
        if(chk3.isPresent()){
            throw  new UserSystemException(UserSystemStateCode.Email_IsExites);
        }
        //返回值
        User dbrtn_user=userRepository.save(user);
        User rtn_user=new User();
        rtn_user.setRowId(dbrtn_user.getRowId());
        rtn_user.setUsername(dbrtn_user.getUsername());
        rtn_user.setMobile(dbrtn_user.getMobile());
        rtn_user.setEmail(dbrtn_user.getEmail());
        rtn_user.setAdmin(dbrtn_user.getAdmin());
        rtn_user.setEnable(dbrtn_user.getEnable());
        return  rtn_user;
    }
    //编辑用户
    @Override
    public User modifyUser(User user) {
        //判断电话号码，邮箱,用户id是否为空
       if(user.getMobile()==null||user.getMobile().replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.Mobile_IsEmpty);
        }else if(user.getEmail()==null||user.getEmail().replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.Email_IsEmpty);
        }else if(user.getRowId()==null||user.getRowId().replaceAll("\\s*","").equals("")){
           throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        //判断用户是否存在
        Optional<User>  userOptional = userRepository.findById(user.getRowId());
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        //判断号码是否占用
        Optional<User>  mobileOptional = userRepository.findByMobile(user.getMobile());

        if(mobileOptional.isPresent()&&mobileOptional.get().getRowId() != user.getRowId()){
            throw new UserSystemException(UserSystemStateCode.Mobile_IsExites);
        }
        //判断邮箱是否占用
        Optional<User>  emailOptional = userRepository.findByEmail(user.getEmail());
        if(emailOptional.isPresent()&&emailOptional.get().getRowId()!=user.getRowId()){
            throw new UserSystemException(UserSystemStateCode.Email_IsExites);
        }
        User dbUser = userOptional.get();
        dbUser.setMobile(user.getMobile());
        dbUser.setEmail(user.getEmail());
        //密码 最后登录时间 Todo
        return userRepository.save(dbUser);
    }
    //删除用户
    @Override
    public void deleteUser(String rowId) {
        if(rowId==null||rowId.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        Optional<User> optional=userRepository.findById(rowId);
        if(!optional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        userRepository.deleteById(rowId);
    }
    //提升/降级管理员标识
    @Override
    public void setAdmin(String rowId, Boolean isAdmin) {
        if(rowId==null||rowId.replaceAll("\\s","").equals("")){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }else if(isAdmin==null){
            throw new UserSystemException(UserSystemStateCode.IsAdmin_IsEmpty);
        }
        Optional<User> optional= userRepository.findById(rowId);
        if(!optional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User user=optional.get();
        user.setAdmin(isAdmin);
        userRepository.save(user);
    }
    //修改密码
    @Override
    public void changePassword(String rowId, String oldPassword, String newPassword) {
        //判断用户id，旧密码，新密码是否为空
        if(rowId==null||rowId.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }else if (oldPassword==null||oldPassword.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.OldPassword_IsEmpty);
        }else if (newPassword==null||newPassword.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.NewPassword_IsEmpty);
        }
        Optional<User> userOptional=userRepository.findById(rowId);
        //判断用户是否存在
        if (!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User dbUser = userOptional.get();
        User chkUser = new User();
        chkUser.setPassword(oldPassword);
        //判断旧密码是否正确
        if(!dbUser.getPasswordHash() .equals(chkUser.getPasswordHash())){
            throw new UserSystemException(UserSystemStateCode.Password_isErr);
        }
        //判断新密码是否与旧密码相同
        if(oldPassword.equals(newPassword)){
            throw new UserSystemException(UserSystemStateCode.Password_isReqeat);
        }
        dbUser.setPassword(newPassword);
        userRepository.save(dbUser);
    }
    //启用禁用用户
    @Override
    public void setEnable(String rowId, Boolean isEnable) {
        if(rowId==null||rowId.replaceAll("\\s","").equals("")){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }else if(isEnable==null){
            throw new UserSystemException(UserSystemStateCode.IsEnable_IsEmpty);
        }
        Optional<User>  userOptional = userRepository.findById(rowId);
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User user=userOptional.get();
        user.setEnable(isEnable);
        userRepository.save(user);
    }
    //重置密码
    @Override
    public void resetPassword(String rowId, String newPassword) {
        //判断用户id，新密码是否为空
        if(rowId==null||rowId.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }else if(newPassword==null||newPassword.replaceAll("\\s*","").equals("")){
            throw new UserSystemException(UserSystemStateCode.NewPassword_IsEmpty);
        }
        //用户是否存在
        Optional<User>  userOptional=userRepository.findById(rowId);
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User user=userOptional.get();
        user.setPassword(newPassword);
        userRepository.save(user);
    }

}
