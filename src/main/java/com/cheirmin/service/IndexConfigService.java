package com.cheirmin.service;

import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.pojo.IndexConfig;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:37
 * @Version 1.0
 */
public interface IndexConfigService {
       PageInfo queryallIndexConfig(Integer indexpage,Integer pagesize);
       boolean updateIndexConfig(IndexConfig indexConfig);
       boolean addIndexConfig(IndexConfig indexConfig);
       boolean deleteIndexConfig(IndexConfig indexConfig);

       List<IndexConfig> queryIndexConfig();
       List<IndexConfig> queryIndexConfig(String hot);
}
