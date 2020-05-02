package com.cheirmin.service;

import com.cheirmin.vo.IndexConfigBooksVO;
import com.cheirmin.pojo.IndexConfig;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:37
 * @Version 1.0
 */
public interface IndexConfigService {
       PageInfo queryallIndexConfig(Integer indexpage,Integer pagesize,String configType);
       boolean updateIndexConfig(IndexConfig indexConfig,HttpServletRequest request);
       boolean updateIndexConfigByids(List<Long> ids,HttpServletRequest request);
       boolean addIndexConfig(IndexConfig indexConfig, HttpServletRequest request);
       boolean deleteIndexConfig(IndexConfig indexConfig);
       List<IndexConfigBooksVO> getConfigBooksesForIndex(int configType, int number);


       List<IndexConfig> queryIndexConfig();
       List<IndexConfig> queryIndexConfig(String hot);
}
