package com.cheirmin.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Message:书本详情页VO
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:13
 * @Version 1.0
 */
public class BooksDetailVO implements Serializable {

    private Integer stockNum;

    private String tag;

    private Long bookId;

    private String bookName;

    private String bookIntro;

    private String publishingHouse;

    private String bookCoverImg;

    private String bookAuthor;

    private Long bookCategoryId1;

    private Long bookCategoryId2;

    private Long bookCategoryId3;

    private String[] bookCarouselList;

    private Integer sellingPrice;

    private Integer originalPrice;

    private String bookDetailContent;

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
        this.tag = tag;
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

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(String bookCoverImg) {
        this.bookCoverImg = bookCoverImg;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
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

    public String[] getBookCarouselList() {
        return bookCarouselList;
    }

    public void setBookCarouselList(String[] bookCarouselList) {
        this.bookCarouselList = bookCarouselList;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getBookDetailContent() {
        return bookDetailContent;
    }

    public void setBookDetailContent(String bookDetailContent) {
        this.bookDetailContent = bookDetailContent;
    }
}
