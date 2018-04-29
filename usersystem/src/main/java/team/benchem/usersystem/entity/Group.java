package team.benchem.usersystem.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name="t_sys_funcgroup")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fchanneldid")
    Channel ownerChannel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerGroup")
    Set<Functional> functions = new HashSet<>();

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

    public Set<Functional> getFunctions() {
        return functions;
    }
}
