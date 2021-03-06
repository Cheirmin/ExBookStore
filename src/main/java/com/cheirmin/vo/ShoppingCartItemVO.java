package com.cheirmin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Message:购物车页面购物项VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:19
 * @Version 1.0
 */
public class ShoppingCartItemVO implements Serializable {

    private Long cartItemId;

    private Long userId;

    private Long bookId;

    private String bookCoverImg;

    private String bookName;

    private Integer bookCount;

    private BigDecimal sellingPrice;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public String toString() {
        return "ShoppingCartItemVO{" +
                "cartItemId=" + cartItemId +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", bookCoverImg='" + bookCoverImg + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookCount=" + bookCount +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
