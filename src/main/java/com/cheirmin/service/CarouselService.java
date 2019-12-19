package com.cheirmin.service;

import com.cheirmin.pojo.IndexCarousel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:36
 * @Version 1.0
 */

//轮播图的管理
public interface CarouselService {
    PageInfo queryallCarousel(Integer indexpage, Integer pagesize);
    boolean updateCarousel(IndexCarousel indexCarousel);
    boolean deleteCarousel(IndexCarousel indexCarousel);
    boolean addCarousel(IndexCarousel indexCarousel);
    List<IndexCarousel> queryCarouselBySort();
}
