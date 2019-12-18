package com.cheirmin.dao;

import com.cheirmin.pojo.IndexCarousel;

public interface IndexCarouselMapper {
    int deleteByPrimaryKey(Integer carouselId);

    int insert(IndexCarousel record);

    int insertSelective(IndexCarousel record);

    IndexCarousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(IndexCarousel record);

    int updateByPrimaryKey(IndexCarousel record);
}