package com.cheirmin.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(useGeneratedKeys = true)
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "book_intro")
    private String bookIntro;

    @Column(name = "book_abstract")
    private String bookAbstract;

    @Column(name = "book_category_id1")
    private Long bookCategoryId1;

    @Column(name = "book_category_id2")
    private Long bookCategoryId2;

    @Column(name = "book_category_id3")
    private Long bookCategoryId3;

    @Column(name = "book_cover_img")
    private String bookCoverImg;

    @Column(name = "book_carousel")
    private String bookCarousel;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "selling_price")
    private BigDecimal  sellingPrice;

    @Column(name = "stock_num")
    private Integer stockNum;

    @Column(name = "book_sell_status")
    private Byte bookSellStatus;

    @Column(name = "create_user")
    private Integer createUser;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Column(name = "update_user")
    private Integer updateUser;

    @Column(name = "book_detail_content")
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

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
    }

    public String getBookAbstract() {
        return bookAbstract;
    }

    public void setBookAbstract(String bookAbstract) {
        this.bookAbstract = bookAbstract;
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

    public BigDecimal  getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal  originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal  getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal  sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
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

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookIntro='" + bookIntro + '\'' +
                ", bookAbstract='" + bookAbstract + '\'' +
                ", bookCategoryId1=" + bookCategoryId1 +
                ", bookCategoryId2=" + bookCategoryId2 +
                ", bookCategoryId3=" + bookCategoryId3 +
                ", bookCoverImg='" + bookCoverImg + '\'' +
                ", bookCarousel='" + bookCarousel + '\'' +
                ", originalPrice=" + originalPrice +
                ", sellingPrice=" + sellingPrice +
                ", stockNum=" + stockNum +
                ", bookSellStatus=" + bookSellStatus +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", bookDetailContent='" + bookDetailContent + '\'' +
                '}';
    }
}