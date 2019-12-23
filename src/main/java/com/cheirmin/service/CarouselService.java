package com.cheirmin.service;

import com.cheirmin.pojo.IndexCarousel;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
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
    IndexCarousel selectoneCarousel(Integer id);
    boolean updateCarousel(IndexCarousel indexCarousel,HttpServletRequest request);
    boolean deleteCarousel(String ids);
    boolean updateCarouselByids(String ids,HttpServletRequest request);
    boolean addCarousel(IndexCarousel indexCarousel, HttpServletRequest request);
    List<IndexCarousel> queryCarouselBySort();
}
