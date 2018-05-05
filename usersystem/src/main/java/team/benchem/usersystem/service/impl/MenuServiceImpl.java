package team.benchem.usersystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.benchem.framework.lang.MicroServiceException;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.entity.Functional;
import team.benchem.usersystem.entity.Group;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.lang.UserSystemStateCode;
import team.benchem.usersystem.repository.ChannelRepository;
import team.benchem.usersystem.repository.FunctionalRepository;
import team.benchem.usersystem.repository.GroupRepository;
import team.benchem.usersystem.repository.PermissionRepository;
import team.benchem.usersystem.service.MenuService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackOn = {MicroServiceException.class, RuntimeException.class})
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
        List<Channel> existChannels = channelRepository.findAllByChannelKeyOrChannelName(
                channel.getChannelKey(),
                channel.getChannelName());
        if(existChannels.size() > 0){
            throw new UserSystemException(UserSystemStateCode.Channel_IsExist);
        }
        channelRepository.save(channel);
        return channel;
    }

    @Override
    public void modifyChannel(Channel channel) {
        Channel existChannel = channelRepository.findByChannelKeyOrChannelNameIsNotExits(
                channel.getChannelKey(),
                channel.getChannelName(),
                channel.getRowId()
        );
        if(existChannel != null){
            throw new UserSystemException(UserSystemStateCode.Channel_IsExist);
        }
        channelRepository.save(channel);
    }

    @Override
    public void deleteChannel(String channelId) {
        Optional<Channel> channelOptional = channelRepository.findById(channelId);
        if(!channelOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.Channel_IsNotExist);
        }
        channelRepository.delete(channelOptional.get());
    }

    @Override
    public Group appendGroup(String channelId, Group group) {
        Optional<Channel> channelOptional = channelRepository.findById(channelId);
        if(!channelOptional.isPresent()){
            throw new UserSystemException(UserSystemStateCode.Channel_IsNotExist);
        }
        Group groupOptional = groupRepository.findByOwnerChannelNotExits(
                channelId,
                group.getRowId(),
                group.getGroupKey(),
                group.getGroupName()
        );
        if(groupOptional != null){
            throw new UserSystemException(UserSystemStateCode.Group_IsExist);
        }

        Channel ownerCh = channelOptional.get();
        group.setOwnerChannelId(channelId);
        ownerCh.getGroups().add(group);
        channelRepository.save(ownerCh);
        return group;
    }

    @Override
    public void modifyGroup(Group group) {

    }

    @Override
    public void deleteGroup(String groupId) {

    }

    @Override
    public Functional appendFunctional(Functional functional) {
        return null;
    }

    @Override
    public void modifyFunctional(Functional functional) {

    }

    @Override
    public void deleteFunctional(String functionalId) {

    }

    @Override
    public List<Channel> getChannels(){
        return channelRepository.findAll(Sort.by("orderIndex"));
    }
}
