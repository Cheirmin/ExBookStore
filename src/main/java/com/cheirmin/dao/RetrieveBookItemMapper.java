package com.cheirmin.dao;

import com.cheirmin.pojo.RetrieveBookItem;

public interface RetrieveBookItemMapper {
    int insert(RetrieveBookItem record);

    int insertSelective(RetrieveBookItem record);
}