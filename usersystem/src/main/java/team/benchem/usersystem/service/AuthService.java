package team.benchem.usersystem.service;

import team.benchem.usersystem.entity.Channel;

import java.util.List;

public interface AuthService {

    String login(String userName, String password);

    String sayOnline(String userName, String token);

    void logout(String userName, String token);

    List<Channel> getUserMenus(String userName);
}
