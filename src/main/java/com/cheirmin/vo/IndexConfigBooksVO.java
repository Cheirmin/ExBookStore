package com.cheirmin.vo;

import java.io.Serializable;

/**
 * @Message:首页配置商品VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:14
 * @Version 1.0
 */
public class IndexConfigBooksVO implements Serializable {

    private Long bookId;

    private String bookName;

    private String bookIntro;

    private String bookCoverImg;

    private Integer sellingPrice;

    private String tag;

    private String bookAuthor;

    private String publishingHouse;

    private Integer originalPrice;

    private Integer stockNum;

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
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
        this.bookName = bookName;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
