package com.cheirmin.dao;

import com.cheirmin.pojo.RetrieveOrderItem;

public interface RetrieveOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(RetrieveOrderItem record);

    int insertSelective(RetrieveOrderItem record);

    RetrieveOrderItem selectByPrimaryKey(Long orderItemId);

    int updateByPrimaryKeySelective(RetrieveOrderItem record);

    int updateByPrimaryKey(RetrieveOrderItem record);
}