package com.cheirmin.service.impl;

import com.cheirmin.common.CategoryLevelEnum;
import com.cheirmin.common.Constants;
import com.cheirmin.vo.IndexCategoryVO;
import com.cheirmin.vo.SearchPageCategoryVO;
import com.cheirmin.vo.SecondLevelCategoryVO;
import com.cheirmin.vo.ThirdLevelCategoryVO;
import com.cheirmin.dao.BooksCategoryMapper;
import com.cheirmin.pojo.BooksCategory;
import com.cheirmin.service.CategoryService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Message:分类
 * @Author：Cheirmin
 * @Date: 2019/12/18 11:09
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private BooksCategoryMapper booksCategoryMapper;

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        Example example=new Example(BooksCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",pageUtil.get("parentId"));
        criteria.andEqualTo("categoryLevel",pageUtil.get("categoryLevel"));

        example.setOrderByClause("`category_rank` DESC");

        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<BooksCategory> booksCategories = booksCategoryMapper.selectByExampleAndRowBounds(example,rowBounds);

        int total = booksCategoryMapper.selectCountByExample(example);

        PageResult pageResult = new PageResult(booksCategories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCategory(BooksCategory booksCategory) {
        return null;
    }

    @Override
    public String updateGoodsCategory(BooksCategory booksCategory) {
        return null;
    }

    @Override
    public BooksCategory getBooksCategoryById(Long id) {
        return booksCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return null;
    }

    @Override
    public List<IndexCategoryVO> getCategoriesForIndex() {

        List<IndexCategoryVO> IndexCategoryVOS = new ArrayList<>();

        /**  BEGIN 获取一级分类的固定数量的数据   */
        Example example=new Example(BooksCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("parentId",Collections.singletonList(0L));
        criteria.andEqualTo("categoryLevel",CategoryLevelEnum.LEVEL_ONE.getLevel());
        example.setOrderByClause("`category_rank` DESC");
        RowBounds rowBounds = new RowBounds(0, Constants.INDEX_CATEGORY_NUMBER);
        List<BooksCategory> firstLevelCategories = booksCategoryMapper.selectByExampleAndRowBounds(example,rowBounds);
        /**  END 获取一级分类的固定数量的数据   */

        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(BooksCategory::getCategoryId).collect(Collectors.toList());

            /**  BEGIN 获取二级分类的数据   */
            Example example1=new Example(BooksCategory.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andIn("parentId",firstLevelCategoryIds);
            criteria1.andEqualTo("categoryLevel", CategoryLevelEnum.LEVEL_TWO.getLevel());
            example1.setOrderByClause("`category_rank` DESC");
            List<BooksCategory> secondLevelCategories = booksCategoryMapper.selectByExample(example1);
            /**  END 获取二级分类的数据   */

            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(BooksCategory::getCategoryId).collect(Collectors.toList());

                /**  BEGIN 获取三级分类的数据   */
                Example example2 = new Example(BooksCategory.class);
                Example.Criteria criteria2 = example2.createCriteria();
                criteria2.andIn("parentId",secondLevelCategoryIds);
                criteria2.andEqualTo("categoryLevel",CategoryLevelEnum.LEVEL_THREE.getLevel());
                example2.setOrderByClause("`category_rank` DESC");
                List<BooksCategory> thirdLevelCategories = booksCategoryMapper.selectByExample(example2);
                /**  END 获取三级分类的数据   */

                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<BooksCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(BooksCategory::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (BooksCategory secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<BooksCategory> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    //处理一级分类
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                        for (BooksCategory firstCategory : firstLevelCategories) {
                            IndexCategoryVO newBeeMallIndexCategoryVO = new IndexCategoryVO();
                            BeanUtil.copyProperties(firstCategory, newBeeMallIndexCategoryVO);
                            //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<SecondLevelCategoryVO> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                newBeeMallIndexCategoryVO.setSecondLevelCategoryVOS(tempGoodsCategories);
                                IndexCategoryVOS.add(newBeeMallIndexCategoryVO);
                            }
                        }
                    }
                }
            }
            return IndexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public SearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        SearchPageCategoryVO searchPageCategoryVO = new SearchPageCategoryVO();
        BooksCategory thirdLevelBooksCategory = booksCategoryMapper.selectByPrimaryKey(categoryId);

        if (thirdLevelBooksCategory != null && thirdLevelBooksCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            //获取当前三级分类的二级分类
            BooksCategory secondLevelBooksCategory = booksCategoryMapper.selectByPrimaryKey(thirdLevelBooksCategory.getParentId());

            if (secondLevelBooksCategory != null && secondLevelBooksCategory.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
                //获取当前二级分类下的三级分类List
                Example example=new Example(BooksCategory.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("parentId",Collections.singletonList(secondLevelBooksCategory.getCategoryId()));
                criteria.andEqualTo("categoryLevel",CategoryLevelEnum.LEVEL_THREE.getLevel());
                example.setOrderByClause("`category_rank` DESC");
                RowBounds rowBounds = new RowBounds(0, Constants.SEARCH_CATEGORY_NUMBER);
                List<BooksCategory> thirdLevelCategories = booksCategoryMapper.selectByExampleAndRowBounds(example,rowBounds);

                searchPageCategoryVO.setCurrentCategoryName(thirdLevelBooksCategory.getCategoryName());
                searchPageCategoryVO.setSecondLevelCategoryName(secondLevelBooksCategory.getCategoryName());
                searchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return searchPageCategoryVO;
            }
        }
        return null;
    }

    @Override
    public List<BooksCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
        Example example=new Example(BooksCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("parentId",parentIds);
        criteria.andEqualTo("categoryLevel",categoryLevel);

        return booksCategoryMapper.selectByExample(example);
    }


}
