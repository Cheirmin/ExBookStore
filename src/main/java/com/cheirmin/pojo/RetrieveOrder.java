package com.cheirmin.pojo;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_retrieve_order")
public class RetrieveOrder {
    private Long orderRetrieveId;

    private String orderRetrieveNo;

    private Long userId;

    private String userPhone;

    private Integer totalPrice;

    private Byte payStatus;

    private String payPhoto;

    private Date payTime;

    private Byte orderStatus;

    private String extraInfo;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;

    public Long getOrderRetrieveId() {
        return orderRetrieveId;
    }

    public void setOrderRetrieveId(Long orderRetrieveId) {
        this.orderRetrieveId = orderRetrieveId;
    }

    public String getOrderRetrieveNo() {
        return orderRetrieveNo;
    }

    public void setOrderRetrieveNo(String orderRetrieveNo) {
        this.orderRetrieveNo = orderRetrieveNo == null ? null : orderRetrieveNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayPhoto() {
        return payPhoto;
    }

    public void setPayPhoto(String payPhoto) {
        this.payPhoto = payPhoto == null ? null : payPhoto.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Byte getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Byte orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo == null ? null : extraInfo.trim();
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}