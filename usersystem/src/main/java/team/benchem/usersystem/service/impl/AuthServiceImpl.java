package team.benchem.usersystem.service.impl;

import org.springframework.stereotype.Service;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.service.AuthService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public String login(String userName, String password) {
        return null;
    }

    @Override
    public String sayOnline(String userName, String token) {
        return null;
    }

    @Override
    public void logout(String userName, String token) {

    }

    @Override
    public List<Channel> getUserMenus(String userName) {
        return null;
    }
}
