package com.cheirmin.pojo;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_retrieve_books_item")
public class RetrieveBookItem {
    private Long itemId;

    private Long userId;

    private Long bookId;

    private String bookName;

    private String bookCoverImg;

    private Integer sellingPrice;

    private Integer bookCount;

    private Integer retievedCount;

    private Date createTime;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Integer getRetievedCount() {
        return retievedCount;
    }

    public void setRetievedCount(Integer retievedCount) {
        this.retievedCount = retievedCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}