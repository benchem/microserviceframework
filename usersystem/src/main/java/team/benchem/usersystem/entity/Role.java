package team.benchem.usersystem.entity;

import javax.persistence.Id;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="t_sys_role")
public class Role {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="frolename")
    String rolename;

    @Column(name="fisenable")
    Boolean isEnable;

    @Column(name="fdescribe")
    String describe;

    @ManyToMany(cascade = CascadeType.DETACH)
    @OrderBy(value="username")
    @JoinTable(
        name = "t_sys_rolemapping",
        joinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "frowid"),
        inverseJoinColumns = @JoinColumn(name = "UserId", referencedColumnName = "frowid")
    )
    List<User> Users = new ArrayList<>();

    public Role() {
        rowId = UUID.randomUUID().toString();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<User> getUsers() {
        return Users;
    }
}
