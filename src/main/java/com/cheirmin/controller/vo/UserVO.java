package com.cheirmin.controller.vo;

import java.io.Serializable;

/**
 * @Message:用户VO,用于登陆存于session
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:19
 * @Version 1.0
 */
public class UserVO implements Serializable {
    private Long userId;

    private String nickName;

    private String userEmail;

    private String userPhone;

    private String introduceSign;

    private String address;

    private int shopCartItemCount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIntroduceSign() {
        return introduceSign;
    }

    public void setIntroduceSign(String introduceSign) {
        this.introduceSign = introduceSign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShopCartItemCount() {
        return shopCartItemCount;
    }

    public void setShopCartItemCount(int shopCartItemCount) {
        this.shopCartItemCount = shopCartItemCount;
    }
}
