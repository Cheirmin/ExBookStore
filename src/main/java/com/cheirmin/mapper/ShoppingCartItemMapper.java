package com.cheirmin.mapper;

import com.cheirmin.pojo.ShoppingCartItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ShoppingCartItemMapper extends Mapper<ShoppingCartItem> {
}
