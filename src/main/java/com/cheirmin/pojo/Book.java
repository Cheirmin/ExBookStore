package com.cheirmin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_books")
public class Book {
    private Long bookId;

    private String bookName;

    private String bookIntro;

    private String bookAuthor;

    private String publishingHouse;

    private Long bookCategoryId1;

    private Long bookCategoryId2;

    private Long bookCategoryId3;

    private String bookCoverImg;

    private String bookCarousel;

    private Integer originalPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private String tag;

    private Byte bookSellStatus;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer updateUser;

    private String bookDetailContent;

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

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse == null ? null : publishingHouse.trim();
    }

    public Long getBookCategoryId1() {
        return bookCategoryId1;
    }

    public void setBookCategoryId1(Long bookCategoryId1) {
        this.bookCategoryId1 = bookCategoryId1;
    }

    public Long getBookCategoryId2() {
        return bookCategoryId2;
    }

    public void setBookCategoryId2(Long bookCategoryId2) {
        this.bookCategoryId2 = bookCategoryId2;
    }

    public Long getBookCategoryId3() {
        return bookCategoryId3;
    }

    public void setBookCategoryId3(Long bookCategoryId3) {
        this.bookCategoryId3 = bookCategoryId3;
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg == null ? null : bookCoverImg.trim();
    }

    public String getBookCarousel() {
        return bookCarousel;
    }

    public void setBookCarousel(String bookCarousel) {
        this.bookCarousel = bookCarousel == null ? null : bookCarousel.trim();
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Byte getBookSellStatus() {
        return bookSellStatus;
    }

    public void setBookSellStatus(Byte bookSellStatus) {
        this.bookSellStatus = bookSellStatus;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getBookDetailContent() {
        return bookDetailContent;
    }

    public void setBookDetailContent(String bookDetailContent) {
        this.bookDetailContent = bookDetailContent == null ? null : bookDetailContent.trim();
    }
}