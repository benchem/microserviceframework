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
import team.benchem.usersystem.lang.UserSystemException;
import team.benchem.usersystem.service.MenuService;

import java.util.List;

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
        ch1.setChannelKey("AA");
        menuService.appendChannel(ch2);
    }

    @Test
    public void Test2(){
        List<Channel> channelList = menuService.getChannels();
        Assert.assertEquals(channelList.size(), 1);
        Assert.assertEquals(channelList.get(0).getOrderIndex(), (Integer) 10);
    }
}
