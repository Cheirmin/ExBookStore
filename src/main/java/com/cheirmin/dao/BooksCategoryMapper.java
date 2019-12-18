package com.cheirmin.dao;

import com.cheirmin.pojo.BooksCategory;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BooksCategoryMapper extends Mapper<BooksCategory> {

    int deleteByPrimaryKey(Long categoryId);

    int insert(BooksCategory record);

    int insertSelective(BooksCategory record);

    BooksCategory selectByPrimaryKey(Long categoryId);

    int updateByPrimaryKeySelective(BooksCategory record);

    int updateByPrimaryKey(BooksCategory record);
}