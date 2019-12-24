package com.cheirmin.dao;

import com.cheirmin.pojo.Book;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BookMapper extends Mapper<Book> , IdListMapper<Book,Long> {

//    @Update(value = "update tb_books set book_sell_status=1 where id in #{list}")
//    int batchupdate(List<Long> list);

    int insert(Book record);

    int insertSelective(Book record);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}