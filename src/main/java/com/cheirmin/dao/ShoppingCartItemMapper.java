package com.cheirmin.dao;

import com.cheirmin.pojo.ShoppingCartItem;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ShoppingCartItemMapper extends Mapper<ShoppingCartItem> {
}