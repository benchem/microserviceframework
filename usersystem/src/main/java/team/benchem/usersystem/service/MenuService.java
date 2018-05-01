package team.benchem.usersystem.service;

import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.entity.Functional;
import team.benchem.usersystem.entity.Group;

import java.util.List;

public interface MenuService {

    Channel appendChannel(Channel channel);

    void modifyChannel(Channel channel);

    void deleteChannel(String channelId);

    Group appendGroup(String channelId, Group group);

    void modifyGroup(Group group);

    void deleteGroup(String groupId);

    Functional appendFunctional(Functional functional);

    void modifyFunctional(Functional functional);

    void deleteFunctional(String functionalId);

    List<Channel> getChannels();
}
