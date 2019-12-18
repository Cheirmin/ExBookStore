package com.cheirmin.dao;

import com.cheirmin.pojo.BooksReview;

public interface BooksReviewMapper {
    int deleteByPrimaryKey(Long reviewsId);

    int insert(BooksReview record);

    int insertSelective(BooksReview record);

    BooksReview selectByPrimaryKey(Long reviewsId);

    int updateByPrimaryKeySelective(BooksReview record);

    int updateByPrimaryKey(BooksReview record);
}