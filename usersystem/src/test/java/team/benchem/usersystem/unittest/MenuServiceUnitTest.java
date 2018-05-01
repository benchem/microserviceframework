package team.benchem.usersystem.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import team.benchem.usersystem.entity.Channel;
import team.benchem.usersystem.entity.Group;
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.service.MenuService;

import java.util.List;
import java.util.Set;

@DataJpaTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceUnitTest {

    @Autowired
    MenuService menuService;

    @Before
    public void beforeTest(){
        Channel ch = new Channel();
        ch.setChannelKey("Channel_SCM");
        ch.setChannelName("供应链");
        ch.setOrderIndex(10);
        menuService.appendChannel(ch);
    }

    @Test(expected = UserSystemException.class)
    public void Test1(){
        Channel ch1 = new Channel();
        ch1.setChannelKey("AA");
        ch1.setOrderIndex(99);
        menuService.appendChannel(ch1);

        Channel ch2 = new Channel();
        ch1.setChannelKey("aa");
        menuService.appendChannel(ch2);
    }

    @Test
    public void Test2(){
        List<Channel> channelList = menuService.getChannels();
        Assert.assertEquals(channelList.size(), 1);
        Assert.assertEquals(channelList.get(0).getOrderIndex(), (Integer) 10);
    }

    @Test
    public void Test3(){
        Channel ch = new Channel();
        ch.setChannelKey("Channel_Test");
        ch.setChannelName("AAA");
        ch.setOrderIndex(1);
        List<Group> funcGroup = ch.getGroups();

        Group group1 = new Group();
        group1.setGroupKey("Group_AAA");
        group1.setGroupName("GroupA");
        funcGroup.add(group1);

        menuService.appendChannel(ch);
        List<Channel> channelList = menuService.getChannels();
        Channel dbCh = channelList.get(0);
        Assert.assertEquals(dbCh.getGroups().size(), 1);
    }

    @Test
    public void Test4(){
        Channel ch1 = new Channel();
        ch1.setChannelKey("AA");
        ch1.setOrderIndex(99);
        menuService.appendChannel(ch1);

        ch1.setChannelKey("BB");
        menuService.modifyChannel(ch1);

        List<Channel> channelList = menuService.getChannels();
        Assert.assertEquals(channelList.get(1).getChannelKey(), "BB");
    }
}
