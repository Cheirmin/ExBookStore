package com.cheirmin.dao;

import com.cheirmin.pojo.RetrieveOrder;

public interface RetrieveOrderMapper {
    int deleteByPrimaryKey(Long orderRetrieveId);

    int insert(RetrieveOrder record);

    int insertSelective(RetrieveOrder record);

    RetrieveOrder selectByPrimaryKey(Long orderRetrieveId);

    int updateByPrimaryKeySelective(RetrieveOrder record);

    int updateByPrimaryKey(RetrieveOrder record);
}