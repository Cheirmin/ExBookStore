package com.cheirmin.dao;

import com.cheirmin.pojo.Book;
import tk.mybatis.mapper.common.Mapper;

public interface BookMapper extends Mapper<Book> {

    int insert(Book record);

    int insertSelective(Book record);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}