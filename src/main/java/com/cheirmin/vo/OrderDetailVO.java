package com.cheirmin.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Message:订单详情页页面VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:18
 * @Version 1.0
 */
public class OrderDetailVO implements Serializable {
    private String orderNo;

    private Integer totalPrice;

    private Byte payStatus;

    private String payStatusString;

    private Byte payType;

    private String payTypeString;

    private Date payTime;

    private Byte orderStatus;

    private String orderStatusString;

    private String userAddress;

    private Date createTime;

    private List<OrderItemVO> orderItemVOS;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getPayStatusString() {
        return payStatusString;
    }

    public void setPayStatusString(String payStatusString) {
        this.payStatusString = payStatusString;
    }

    public Byte getPayType() {
        return payType;
    }

    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    public String getPayTypeString() {
        return payTypeString;
    }

    public void setPayTypeString(String payTypeString) {
        this.payTypeString = payTypeString;
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

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<OrderItemVO> getOrderItemVOS() {
        return orderItemVOS;
    }

    public void setOrderItemVOS(List<OrderItemVO> orderItemVOS) {
        this.orderItemVOS = orderItemVOS;
    }

    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "orderNo='" + orderNo + '\'' +
                ", totalPrice=" + totalPrice +
                ", payStatus=" + payStatus +
                ", payStatusString='" + payStatusString + '\'' +
                ", payTypeString='" + payTypeString + '\'' +
                ", payTime=" + payTime +
                ", orderStatus=" + orderStatus +
                ", orderStatusString='" + orderStatusString + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", createTime=" + createTime +
                ", orderItemVOS=" + orderItemVOS +
                '}';
    }
}
