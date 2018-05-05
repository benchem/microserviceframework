package team.benchem.usersystem.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="t_sys_functional")
public class Functional {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="forderindex")
    Integer orderIndex;

    @Column(name="ffunctionalkey")
    String functionalKey;

    @Column(name="ffunctionalname")
    String functionalName;

    @Column(name="frouterpath")
    String routerPath;

    @Column(name="fownergroupid")
    String ownerGroupId;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy(value="orderIndex")
    @JoinColumn(name="fownerfunctionalid")
    List<Permission> permissions = new ArrayList<>();

    public Functional() {
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

    public String getFunctionalKey() {
        return functionalKey;
    }

    public void setFunctionalKey(String functionalKey) {
        this.functionalKey = functionalKey;
    }

    public String getFunctionalName() {
        return functionalName;
    }

    public void setFunctionalName(String functionalName) {
        this.functionalName = functionalName;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

}
