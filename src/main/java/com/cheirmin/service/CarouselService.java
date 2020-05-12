package com.cheirmin.service;

import com.cheirmin.pojo.IndexCarousel;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:轮播图的管理
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:36
 * @Version 1.0
 */

public interface CarouselService {
    /**
     * 加载所有轮播图
     * @param indexpage
     * @param pagesize
     * @return
     */
    PageInfo queryallCarousel(Integer indexpage, Integer pagesize);

    /**
     * 选择一张轮播图
     * @param id
     * @return
     */
    IndexCarousel selectoneCarousel(Integer id);

    /**
     * 更新修改
     * @param indexCarousel
     * @param request
     * @return
     */
    boolean updateCarousel(IndexCarousel indexCarousel,HttpServletRequest request);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean deleteCarousel(String ids);

    /**
     * 通过id修改
     * @param ids
     * @param request
     * @return
     */
    boolean updateCarouselByids(List<Integer> ids,HttpServletRequest request);

    /**
     * 新增
     * @param indexCarousel
     * @param request
     * @return
     */
    boolean addCarousel(IndexCarousel indexCarousel, HttpServletRequest request);

    /**
     * 按序号大小加载
     * @return
     */
    List<IndexCarousel> queryCarouselBySort();
}
