package team.benchem.usersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.benchem.usersystem.entity.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    @Query("select g from Group g where (g.groupKey = :groupKey or g.groupName = :groupName) and g.rowId <> :groupId and g.ownerChannelId = :channelId")
    Group findByOwnerChannelNotExits(
            @Param("channelId") String channelId,
            @Param("groupId") String groupId,
            @Param("groupKey") String groupKey,
            @Param("groupName") String groupName
    );

}
