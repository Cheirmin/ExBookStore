package com.cheirmin.pojo;

/**
 * @Message:库存修改所需实体
 * @Author：Cheirmin
 * @Date: 2020/2/5 13:31
 * @Version 1.0
 */
public class StockNumDTO {
    private Long bookId;

    private Integer bookCount;

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

    @Override
    public String toString() {
        return "StockNumDTO{" +
                "bookId=" + bookId +
                ", bookCount=" + bookCount +
                '}';
    }
}
