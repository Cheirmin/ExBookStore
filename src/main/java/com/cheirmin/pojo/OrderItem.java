package com.cheirmin.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_order_item")
public class OrderItem {
    @Id
    private Long orderItemId;

    private Long orderId;

    private Long bookId;

    private String bookName;

    private String bookCoverImg;

    private BigDecimal sellingPrice;

    private Integer bookCount;

    private Date createTime;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg == null ? null : bookCoverImg.trim();
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookCoverImg='" + bookCoverImg + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", bookCount=" + bookCount +
                ", createTime=" + createTime +
                '}';
    }
}