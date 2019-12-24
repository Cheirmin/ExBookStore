package com.cheirmin.controller.vo;

import java.io.Serializable;

/**
 * @Message:订单详情页页面订单项VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:18
 * @Version 1.0
 */
public class OrderItemVO implements Serializable {
    private Long bookId;

    private Integer bookCount;

    private String bookName;

    private String bookCoverImg;

    private Integer sellingPrice;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
