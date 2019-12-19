package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.dao.IndexCarouselMapper;
import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.service.CarouselService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarouseServiceImpl implements CarouselService {

    @Resource(type = IndexCarouselMapper.class)
    IndexCarouselMapper indexCarouselMapper;

    @Override
    public PageInfo queryallCarousel(Integer indexpage, Integer pagesize) {
       if (indexpage==null){
           indexpage=1;
       }
       if (pagesize==null){
           pagesize=10;
       }
        PageHelper.startPage(indexpage,pagesize);
        List<IndexCarousel> indexCarousels = indexCarouselMapper.selectAll();
        PageInfo pageInfo=new PageInfo(indexCarousels);
        return pageInfo;
    }

    @Override
    public boolean updateCarousel(IndexCarousel indexCarousel) {
        if (indexCarousel!=null){
            int i = indexCarouselMapper.updateByPrimaryKeySelective(indexCarousel);
            if (i>0){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteCarousel(IndexCarousel indexCarousel) {
        if (indexCarousel!=null){
            int i = indexCarouselMapper.delete(indexCarousel);
            if (i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addCarousel(IndexCarousel indexCarousel) {
        if (indexCarousel!=null){
            int i = indexCarouselMapper.insert(indexCarousel);
            if (i>0){
                return true;
            }
        }
        return false;
    }

//    首页显示排序在前五的轮播图
    @Override
    public List<IndexCarousel> queryCarouselBySort() {
        Example example=new Example(IndexCarousel.class);
        example.setOrderByClause("carousel_rank DESC");
        RowBounds bounds=new RowBounds(0, Constants.INDEX_CAROUSEL_NUMBER);
        List<IndexCarousel> indexCarousels = indexCarouselMapper.selectByExampleAndRowBounds(example, bounds);
        if (indexCarousels!=null&&indexCarousels.size()>0){
            return indexCarousels;
        }
        return null;
    }
}
