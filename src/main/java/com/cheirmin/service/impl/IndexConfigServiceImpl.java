package com.cheirmin.service.impl;

import com.cheirmin.dao.IndexConfigMapper;
import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.service.IndexConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexConfigServiceImpl implements IndexConfigService {

    @Resource
    IndexConfigMapper indexConfigMapper;

    @Override
    public PageInfo queryallIndexConfig(Integer indexpage, Integer pagesize) {
        if (indexpage==null){
            indexpage=1;
        }
        if (pagesize==null){
            pagesize=10;
        }
        PageHelper.startPage(indexpage,pagesize);
        List<IndexConfig> indexConfigs = indexConfigMapper.selectAll();
        PageInfo pageInfo=new PageInfo(indexConfigs);
        if (pageInfo!=null){
            return  pageInfo;
        }
        return null;
    }

    @Override
    public boolean updateIndexConfig(IndexConfig indexConfig) {
        if (indexConfig!=null){
            int i = indexConfigMapper.updateByPrimaryKeySelective(indexConfig);
            if (i>0){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean addIndexConfig(IndexConfig indexConfig) {
        if (indexConfig!=null){
            int i = indexConfigMapper.insertSelective(indexConfig);
            if (i>0){
                return  true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteIndexConfig(IndexConfig indexConfig) {
        if (indexConfig!=null){
            int i = indexConfigMapper.updateByPrimaryKeySelective(indexConfig);
            if (i>0){
                return  true;
            }
        }
        return false;
    }


//    热词显示
//    Criteria:为example提供多个条件的使用
//    RowBounds：可以实现分页查询，就是查询第几条到几条
    @Override
    public List<IndexConfig> queryIndexConfig() {
        Example example=new Example(IndexConfig.class);
        RowBounds bounds=new RowBounds(0, 10);
        example.setOrderByClause("config_rank desc");
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExampleAndRowBounds(example, bounds);
        if (indexConfigs!=null){
            return indexConfigs;
        }
        return null;
    }


//    搜索框热词补充
    @Override
    public List<IndexConfig> queryIndexConfig(String hot) {
        Example example=new Example(IndexConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("config_name",hot+"%");
        RowBounds bounds=new RowBounds(0, 10);
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExampleAndRowBounds(example, bounds);
        if (indexConfigs!=null){
            return indexConfigs;
        }
        return null;
    }
}
