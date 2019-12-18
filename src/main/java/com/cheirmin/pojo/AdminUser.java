package com.cheirmin.pojo;

import javax.persistence.Table;

@Table(name = "tb_user_admin")
public class AdminUser {
    private Integer adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;

    private Byte superAdmin;

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Byte getLocked() {
        return locked;
    }

    public void setLocked(Byte locked) {
        this.locked = locked;
    }

    public Byte getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Byte superAdmin) {
        this.superAdmin = superAdmin;
    }
}