package team.benchem.usersystem.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="t_sys_channel")
public class Channel {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="forderindex")
    Integer orderIndex;

    @Column(name="fchannelkey")
    String channelKey;

    @Column(name="fchannelname")
    String channelName;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy(value="orderIndex")
    @JoinColumn(name="fchannelid")
    List<Group> groups = new ArrayList<>();

    public Channel() {
        rowId = UUID.randomUUID().toString();
        orderIndex = 0;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
