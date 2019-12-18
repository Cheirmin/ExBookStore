package com.cheirmin.pojo;

import java.util.Date;

public class BooksReview {
    private Long reviewsId;

    private String reviewsInfo;

    private Long userId;

    private Long orderItemId;

    private Date createTime;

    private Byte isDeleted;

    private Date deleteTime;

    public Long getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Long reviewsId) {
        this.reviewsId = reviewsId;
    }

    public String getReviewsInfo() {
        return reviewsInfo;
    }

    public void setReviewsInfo(String reviewsInfo) {
        this.reviewsInfo = reviewsInfo == null ? null : reviewsInfo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}