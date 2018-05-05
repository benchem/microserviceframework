package team.benchem.usersystem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.service.AuthService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public String login(String userName, String password) {
        logger.debug("call login %s", userName);
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
