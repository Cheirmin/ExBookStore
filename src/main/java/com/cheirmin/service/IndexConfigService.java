package com.cheirmin.service;

import com.cheirmin.vo.IndexConfigBooksVO;
import com.cheirmin.pojo.IndexConfig;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:首页配置
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:37
 * @Version 1.0
 */
public interface IndexConfigService {
       /**
        * 加载首页配置
        * @param indexpage
        * @param pagesize
        * @param configType
        * @return
        */
       PageInfo queryallIndexConfig(Integer indexpage,Integer pagesize,String configType);

       /**
        * 通过对象改
        * @param indexConfig
        * @param request
        * @return
        */
       boolean updateIndexConfig(IndexConfig indexConfig,HttpServletRequest request);

       /**
        * 通过id改
        * @param ids
        * @param request
        * @return
        */
       boolean updateIndexConfigByids(List<Long> ids,HttpServletRequest request);

       /**
        * 新增
        * @param indexConfig
        * @param request
        * @return
        */
       boolean addIndexConfig(IndexConfig indexConfig, HttpServletRequest request);

       /**
        * 删除
        * @param indexConfig
        * @return
        */
       boolean deleteIndexConfig(IndexConfig indexConfig);

       /**
        * 获取配置信息
        * @param configType
        * @param number
        * @return
        */
       List<IndexConfigBooksVO> getConfigBooksesForIndex(int configType, int number);

       /**
        * 查
        * @return
        */
       List<IndexConfig> queryIndexConfig();

       /**
        * 查
        * @param hot
        * @return
        */
       List<IndexConfig> queryIndexConfig(String hot);
}
