package team.benchem.usersystem.service;

import org.springframework.stereotype.Service;
import team.benchem.usersystem.entity.Channel;

import java.util.List;

public interface MenuService {

    Channel appendChannel(Channel channel);

    List<Channel> getChannels();
}
