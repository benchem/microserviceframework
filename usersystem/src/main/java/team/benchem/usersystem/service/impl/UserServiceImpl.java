package team.benchem.usersystem.service.impl;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import team.benchem.framework.lang.MicroServiceException;
import team.benchem.framework.lang.SystemStateCode;
import team.benchem.usersystem.entity.User;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.lang.UserSystemStateCode;
import team.benchem.usersystem.repository.UserRepository;
import team.benchem.usersystem.service.UserService;

import javax.transaction.SystemException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackOn = {MicroServiceException.class, RuntimeException.class})
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public  List<User> findUsers(String keyword, Integer page, Integer size){
        PageRequest pageable = new PageRequest(page, size);
        return userRepository.findAllByUsernameLikeOrderByUsername(keyword, pageable);
    }

    @Override
    public User appendUser(User user) {
        //判断用户名，邮箱，手机号码是否为空
        if(user.getUsername()==null){
            throw new UserSystemException(UserSystemStateCode.UserName_IsEmpty);
        }
        if(user.getMobile()==null){
            throw new UserSystemException(UserSystemStateCode.Mobile_IsEmpty);
        }
        if(user.getEmail()==null){
            throw new UserSystemException(UserSystemStateCode.Email_IsEmpty);
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
        return  userRepository.save(user);
    }

    @Override
    public User modifyUser(User user) {
        //判断用户是否存在
        Optional<User>  userOptional = userRepository.findById(user.getRowId());
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        //判断号码是否占用
        Optional<User>  userOptional1 = userRepository.findByMobile(user.getMobile());
        if(userOptional1.isPresent()){
            throw new UserSystemException(UserSystemStateCode.Mobile_IsExites);
        }
        //判断邮箱是否占用
        Optional<User>  userOptional2 = userRepository.findByEmail(user.getEmail());
        if(userOptional2.isPresent()){
            throw new UserSystemException(UserSystemStateCode.Email_IsExites);
        }
        User dbUser = userOptional.get();
        dbUser.setMobile(user.getMobile());
        dbUser.setEmail(user.getEmail());
        userRepository.save(dbUser);
        return dbUser;

    }
    //修改密码
    @Override
    public void changepassword(String rowId, String oldPassword, String newPassword) {
        Optional<User> userOptional;
        userOptional=userRepository.findById(rowId);
        User dbUser = userOptional.get();
        User chkUser = new User();
        chkUser.setPassword(oldPassword);
        if(!dbUser.getPasswordHash() .equals(chkUser.getPasswordHash())){
            throw new UserSystemException(UserSystemStateCode.Password_isErr);
        }
        if(oldPassword.equals(newPassword)){
            throw new UserSystemException(UserSystemStateCode.Password_isReqeat);
        }
        System.out.println(chkUser.getPasswordHash());
        dbUser.setPassword(newPassword);
        User ss=userRepository.save(dbUser);
        System.out.println(ss.getPasswordHash());
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
    //提升/降级管理员标识
    @Override
    public void setAdmin(String rowId, Boolean isAdmin) {
        User user=userRepository.findByRowIdAndIsAdmin(rowId,isAdmin);
        user.setAdmin(isAdmin);
        //test用
        User uuu=userRepository.save(user);
        System.out.println(uuu.getRowId());
        System.out.println(uuu.getAdmin());
    }


    //重置密码
    @Override
    public void resetPassword(String userId, String password) {
        Optional<User>  userOptional=userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User user=userOptional.get();
        user.setPassword(password);
        userRepository.save(user);
    }
    //启用禁用用户
    @Override
    public void setEnable(String userId, Boolean isEnable) {
        Optional<User>  userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.User_IsExites);
        }
        User user=userOptional.get();
        user.setEnable(isEnable);
        userRepository.save(user);
    }


}
