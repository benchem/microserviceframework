package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Channel;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    List<Channel> findAllByChannelKeyOrChannelName(String channelKey, String channelName);
}
