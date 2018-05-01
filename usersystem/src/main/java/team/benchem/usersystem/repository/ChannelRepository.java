package team.benchem.usersystem.repository;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Channel;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
    //https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/
    List<Channel> findAllByChannelKeyOrChannelName(String channelKey, String channelName);

    @Query("select c from Channel c where (c.channelKey = :channelKey or c.channelName = :channelName) and c.rowId = :rowId")
    Channel findByChannelKeyOrChannelNameIsNotExits(
            @Param("channelKey") String channelKey,
            @Param("channelName") String channelName,
            @Param("rowId") String rowId);
}
