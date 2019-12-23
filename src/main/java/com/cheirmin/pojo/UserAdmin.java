package com.cheirmin.pojo;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

/**
 * @Filename
 * @auther 吴星辰;
 * @data 2019/12/23 16:18;
 * @Descripion
 * @Version 1.1.1
 * @Function
 * @History
 */
@Table(name="tb_user_admin")
public class UserAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    @Column(name = "admin_user_id")
    private Long userId;

    @Column(name = "nick_name")
    private String nick_name;

    @Column(name="login_password")
    private String password;

    @Column(name="login_user_name")
    private String userEmail;

    @Column(name="locked")
    private Integer locked;

    @Column(name="super_admin")
    private String super_admin;

    public UserAdmin() {
    }

    public UserAdmin(String nick_name, String password, String userEmail, Integer locked, String super_admin) {
        this.nick_name = nick_name;
        this.password = password;
        this.userEmail = userEmail;
        this.locked = locked;
        this.super_admin = super_admin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public String getSuper_admin() {
        return super_admin;
    }

    public void setSuper_admin(String super_admin) {
        this.super_admin = super_admin;
    }

    @Override
    public String toString() {
        return "UserAdmin{" +
                "userId=" + userId +
                ", nick_name='" + nick_name + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", locked=" + locked +
                ", super_admin='" + super_admin + '\'' +
                '}';
    }
}
