package com.cheirmin.dao;

import com.cheirmin.pojo.ShoppingCartItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface ShoppingCartItemMapper extends Mapper<ShoppingCartItem> {
}