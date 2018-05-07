package team.benchem.usersystem.service;

import team.benchem.usersystem.entity.User;

import java.util.List;

public interface UserService {
    List<User> findUsers(String keyword, Integer page, Integer size);

    User appendUser(User user);

    User modifyUser(User user);

    void changePassword(String rowId,String oldPassword	,String newPassword);

    void deleteUser(String rowId);

    void setAdmin(String rowId,Boolean isAdmin);

    void resetPassword(String rowId, String newPassword);

    void setEnable(String rowId,  Boolean isEnable);


}
