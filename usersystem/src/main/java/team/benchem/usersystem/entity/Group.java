package team.benchem.usersystem.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="t_sys_funcgroup")
public class Group {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="forderindex")
    Integer orderIndex;

    @Column(name="fgroupkey")
    String groupKey;

    @Column(name="fgroupname")
    String groupName;

    @Column(name="fchannelid")
    String ownerChannelId;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy(value="orderIndex")
    @JoinColumn(name="fownergroupid")
    List<Functional> functions = new ArrayList<>();

    public Group() {
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

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Functional> getFunctions() {
        return functions;
    }

    public String getOwnerChannelId() {
        return ownerChannelId;
    }

    public void setOwnerChannelId(String ownerChannelId) {
        this.ownerChannelId = ownerChannelId;
    }
}
