package com.cheirmin.dao;

import com.cheirmin.pojo.RetrieveBook;

public interface RetrieveBookMapper {
    int deleteByPrimaryKey(Long bookId);

    int insert(RetrieveBook record);

    int insertSelective(RetrieveBook record);

    RetrieveBook selectByPrimaryKey(Long bookId);

    int updateByPrimaryKeySelective(RetrieveBook record);

    int updateByPrimaryKeyWithBLOBs(RetrieveBook record);

    int updateByPrimaryKey(RetrieveBook record);
}