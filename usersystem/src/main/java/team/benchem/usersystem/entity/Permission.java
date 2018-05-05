package team.benchem.usersystem.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="t_sys_funcpermission")
public class Permission {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="forderindex")
    Integer orderIndex;

    @Column(name="fpermissionkey")
    String permissionKey;

    @Column(name="fpermissionname")
    String permissionName;

    @Column(name = "fownerfunctionalid")
    String ownerFunctionalId;

    public Permission() {
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

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getOwnerFunctionalId() {
        return ownerFunctionalId;
    }

    public void setOwnerFunctionalId(String ownerFunctionalId) {
        this.ownerFunctionalId = ownerFunctionalId;
    }
}
