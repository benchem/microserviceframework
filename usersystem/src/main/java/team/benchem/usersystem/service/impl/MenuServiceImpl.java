package team.benchem.usersystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.lang.UserSystemStateCode;
import team.benchem.usersystem.repository.ChannelRepository;
import team.benchem.usersystem.repository.FunctionalRepository;
import team.benchem.usersystem.repository.GroupRepository;
import team.benchem.usersystem.repository.PermissionRepository;
import team.benchem.usersystem.service.MenuService;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FunctionalRepository functionalRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Channel appendChannel(Channel channel){
        List<Channel> exitesChannels = channelRepository.findAllByChannelKeyOrChannelName(channel.getChannelKey(), channel.getChannelName());
        if(exitesChannels.size() > 0){
            throw new UserSystemException(UserSystemStateCode.Channel_IsExites);
        }
        channelRepository.save(channel);
        return channel;
    }

    @Override
    public List<Channel> getChannels(){
        return channelRepository.findAll(Sort.by("orderIndex"));
    }
}
