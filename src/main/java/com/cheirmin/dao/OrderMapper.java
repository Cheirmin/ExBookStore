package com.cheirmin.dao;

import com.cheirmin.pojo.Order;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderMapper extends Mapper<Order> {
}