package team.benchem.usersystem.entity;

import sun.misc.BASE64Encoder;

import javax.persistence.*;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="t_sys_user")
public class User {
    @Id
    @Column(name="frowid", length = 36)
    String rowId;

    @Column(name="fusername")
    String username;

    @Transient
    String password;

    @Column(name="fpasswordhash")
    String passwordHash;

    @Column(name="fmobile")
    String mobile;

    @Column(name="femail")
    String email;

    @Column(name="fisadmin")
    Boolean isAdmin;

    @Column(name="fisenable")
    Boolean isEnable;

    @Column(name="flastlogintime")
    Date lastLoginTime;

    public User() {
        rowId = UUID.randomUUID().toString();
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            BASE64Encoder encoder = new BASE64Encoder();
            this.passwordHash = encoder.encode(digest.digest(this.password.getBytes("utf-8")));
        }catch (Exception ex){
            ex.printStackTrace();
            this.passwordHash = this.password;
        }
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
