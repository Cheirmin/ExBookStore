package com.cheirmin.mapper;

import com.cheirmin.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderItemMapper extends Mapper<OrderItem> {
    @Update(value = "<script>" +
            " insert into tb_order_item (order_id, book_id," +
            "        book_name, book_cover_img, selling_price," +
            "        book_count)" +
            "        values" +
            "        <foreach collection='orderItems' item='orderItem' separator=','>" +
            "            (#{orderItem.orderId,jdbcType=BIGINT}, #{orderItem.bookId,jdbcType=BIGINT}," +
            "            #{orderItem.bookName,jdbcType=VARCHAR}, #{orderItem.bookCoverImg,jdbcType=VARCHAR}," +
            "            #{orderItem.sellingPrice,jdbcType=INTEGER}," +
            "            #{orderItem.bookCount,jdbcType=INTEGER})" +
            "        </foreach></script>")
    int insertBatch(@Param("orderItems") List<OrderItem> orderItems);
}
