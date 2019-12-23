package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.dao.IndexConfigMapper;
import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.service.IndexConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class IndexCofigServiceImpl implements IndexConfigService {

    @Resource
    IndexConfigMapper indexConfigMapper;

//    分页查询热销商品
    @Override
    public PageInfo queryallIndexConfig(Integer indexpage, Integer pagesize,String configType) {
        if (indexpage==null){
            indexpage=1;
        }
        if (pagesize==null){
            pagesize=10;
        }
        Example example=new Example(IndexConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        criteria.andEqualTo("configType",configType);
        PageHelper.startPage(indexpage,pagesize);
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(indexConfigs);
        if (pageInfo!=null){
            return  pageInfo;
        }
        return null;
    }

//修改热销商品
    @Override
    public boolean updateIndexConfig(IndexConfig indexConfig,HttpServletRequest request) {
        if (indexConfig!=null){
            indexConfig.setUpdateTime(new Date());
            if (indexConfig.getUpdateUser()==null){
                Object userId = request.getSession().getAttribute("loginUserId");
                indexConfig.setUpdateUser((Integer) userId);   //如果当前用户没有，默认为1，可从session去取值
            }
            int i = indexConfigMapper.updateByPrimaryKeySelective(indexConfig);
            if (i>0){
                return  true;
            }
        }
        return false;
    }


//    用户点击删除，修改字段id_deleted
    @Override
    public boolean updateIndexConfigByids(String ids,HttpServletRequest request) {
        int i=0;
        if (ids!=null&&ids.length()>0){
            String replace = ids.replace("[", "");
            String s1 = replace.replace("]", "");
            String s = s1.replace("\"", "");
            String[] strings = s.split(",");
            for (String str:strings){
                IndexConfig indexConfig=new IndexConfig();
                indexConfig.setConfigId(Long.valueOf(str));
                indexConfig.setIsDeleted((byte) 1);
                indexConfig.setUpdateTime(new Date());
                if (indexConfig.getUpdateUser()==null){
                    Object userId = request.getSession().getAttribute("loginUserId");
                    indexConfig.setUpdateUser((Integer) userId);   //如果当前用户没有，默认为1，可从session去取值
                }
                i+= indexConfigMapper.updateByPrimaryKeySelective(indexConfig);
            }
            if (i>=strings.length){
                return true;
            }
        }
        return false;
    }

//    增加热销商品
    @Override
    public boolean addIndexConfig(IndexConfig indexConfig, HttpServletRequest request) {
        if (indexConfig!=null){
            indexConfig.setCreateTime(new Date());
            indexConfig.setUpdateTime(new Date());
            indexConfig.setIsDeleted((byte) 0);

//         这里用户需要从session中取值
            Object loginUserId = request.getSession().getAttribute("loginUserId");
            if (indexConfig.getCreateUser()==null){
                indexConfig.setCreateUser((Integer) loginUserId);
            }
            if (indexConfig.getUpdateUser()==null){
                indexConfig.setUpdateUser((Integer) loginUserId);
            }

            int i = indexConfigMapper.insertSelective(indexConfig);
            if (i>0){
                return  true;
            }
        }
        return false;
    }

//    删除热销商品
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
//    查询热销商品前10
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
        System.out.println(hot);
        criteria.orLike("configName",hot+"%");
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExample(example);
        if (indexConfigs!=null){
            return indexConfigs;
        }
        return null;
    }
}
