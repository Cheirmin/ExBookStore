package com.cheirmin.mapper;

import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.StockNumDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface BookMapper extends Mapper<Book> , IdListMapper<Book,Long> {

//    @Update(value = "update tb_books set book_sell_status=1 where id in #{list}")
//    int batchupdate(List<Long> list);

    @Update({"<script><foreach collection ='stockNumDTOS' item ='stockNumDTO'>" +
                "update tb_books set stock_num = stock_num - #{stockNumDTO.bookCount,jdbcType=INTEGER}" +
                " where book_id = #{stockNumDTO.bookId,jdbcType=BIGINT} and stock_num>=#{stockNumDTO.bookCount} and book_sell_status = 1;" +
            "</foreach></script>"})
    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

//    int insert(Book record);
//
//    int insertSelective(Book record);
//
//    int updateByPrimaryKeySelective(Book record);
//
//    int updateByPrimaryKey(Book record);
}
