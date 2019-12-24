package com.cheirmin.service.impl;

import com.cheirmin.controller.vo.IndexConfigBooksVO;
import com.cheirmin.dao.BookMapper;
import com.cheirmin.dao.IndexConfigMapper;
import com.cheirmin.pojo.Book;;
import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.service.IndexConfigService;
import com.cheirmin.util.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexConfigServiceImpl implements IndexConfigService {

    @Resource
    IndexConfigMapper indexConfigMapper;

    @Resource
    BookMapper bookMapper;

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
    public boolean updateIndexConfigByids(List<Long> ids,HttpServletRequest request) {
        if(StringUtils.isEmpty(ids))
            throw new RuntimeException("PARAMS ERROR");
        Example example=new Example(IndexConfig.class);
        example.createCriteria().andIn("carouselId",ids);
        IndexConfig indexConfig=new IndexConfig();
        indexConfig.setIsDeleted((byte)1);
        int res = indexConfigMapper.updateByExampleSelective(indexConfig, example);
        return res > 0;
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

    @Override
    public List<IndexConfigBooksVO> getConfigBooksesForIndex(int configType, int number) {
        List<IndexConfigBooksVO> indexConfigBooksVOS = new ArrayList<>(number);

        Example example = new Example(IndexConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configType",configType);
        example.setOrderByClause("`config_rank` DESC");

        RowBounds bounds=new RowBounds(0, number);

        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExampleAndRowBounds(example,bounds);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的bookIds
            List<Long> bookIds = indexConfigs.stream().map(IndexConfig::getBookId).collect(Collectors.toList());

            Example example1 = new Example(Book.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andIn("bookId",bookIds);

            List<Book> books = bookMapper.selectByExample(example1);

            indexConfigBooksVOS = BeanUtil.copyList(books, IndexConfigBooksVO.class);
            for (IndexConfigBooksVO indexConfigBooksVO : indexConfigBooksVOS) {
                String booksName = indexConfigBooksVO.getBookName();
                String booksIntro = indexConfigBooksVO.getBookIntro();
                // 字符串过长导致文字超出的问题
                if (booksName.length() > 30) {
                    booksName = booksName.substring(0, 30) + "...";
                    indexConfigBooksVO.setBookName(booksName);
                }
                if (booksIntro.length() > 22) {
                    booksIntro = booksIntro.substring(0, 22) + "...";
                    indexConfigBooksVO.setBookIntro(booksIntro);
                }
            }
        }
        return indexConfigBooksVOS;
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
        criteria.orLike("configName",hot+"%");
        List<IndexConfig> indexConfigs = indexConfigMapper.selectByExample(example);
        if (indexConfigs!=null){
            return indexConfigs;
        }
        return null;
    }
}
