package com.cheirmin.mapper;

import com.cheirmin.pojo.Order;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}
