package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.dao.IndexCarouselMapper;
import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.pojo.User;
import com.cheirmin.service.CarouselService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
       Example example=new Example(IndexCarousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        PageHelper.startPage(indexpage,pagesize);
        List<IndexCarousel> indexCarousels = indexCarouselMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(indexCarousels);
        return pageInfo;
    }

    @Override
    public IndexCarousel selectoneCarousel(Integer id) {
        if (id!=null){
            Example example=new Example(IndexCarousel.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("carouselId",id);
            IndexCarousel indexCarousel1 = indexCarouselMapper.selectOneByExample(example);
            if (indexCarousel1!=null){
                return  indexCarousel1;
            }
        }
        return null;
    }

    @Override
    public boolean updateCarousel(IndexCarousel indexCarousel,HttpServletRequest request) {
        if (indexCarousel!=null){
            indexCarousel.setUpdateTime(new Date());
            if (indexCarousel.getUpdateUser()==null){
                Object userId = request.getSession().getAttribute("loginUserId");
                indexCarousel.setUpdateUser((Integer) userId);   //如果当前用户没有，默认为1，可从session去取值
            }
            int i = indexCarouselMapper.updateByPrimaryKeySelective(indexCarousel);
            if (i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteCarousel(String ids) {
        if (ids!=null&&ids.length()>0){
            List<Long> id=new ArrayList<>();
            String replace = ids.replace("[", "");
            String s1 = replace.replace("]", "");
            String s = s1.replace("\"", "");
            String[] strings = s.split(",");
            for (String str:strings){
                id.add(Long.valueOf(str));
            }
            int i = indexCarouselMapper.deleteByIdList(id);
            if (i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateCarouselByids(List<Integer> ids,HttpServletRequest request) {
        if(StringUtils.isEmpty(ids))
            throw new RuntimeException("PARAMS ERROR");
        Example example=new Example(IndexCarousel.class);
        example.createCriteria().andIn("carouselId",ids);
        IndexCarousel indexCarousel=new IndexCarousel();
        indexCarousel.setIsDeleted((byte)1);
        int res = indexCarouselMapper.updateByExampleSelective(indexCarousel, example);
        return res > 0;
    }

    @Override
    public boolean addCarousel(IndexCarousel indexCarousel,HttpServletRequest request) {
        if (indexCarousel!=null){
            indexCarousel.setCreateTime(new Date());
            indexCarousel.setUpdateTime(new Date());
            indexCarousel.setIsDeleted((byte) 0);

//         这里用户需要从session中取值
            Object loginUserId = request.getSession().getAttribute("loginUserId");
            if (indexCarousel.getCreateUser()==null){
                indexCarousel.setCreateUser((Integer) loginUserId);
            }
            if (indexCarousel.getUpdateUser()==null){
                indexCarousel.setUpdateUser((Integer) loginUserId);
            }
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
