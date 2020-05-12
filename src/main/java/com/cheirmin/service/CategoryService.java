package com.cheirmin.service;

import com.cheirmin.vo.IndexCategoryVO;
import com.cheirmin.vo.SearchPageCategoryVO;
import com.cheirmin.pojo.BooksCategory;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;

import java.util.List;

/**
 * @Message:分类
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:36
 * @Version 1.0
 */
public interface CategoryService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    /**
     * 新增
     * @param booksCategory
     * @return
     */
    String saveCategory(BooksCategory booksCategory);

    /**
     * 改
     * @param booksCategory
     * @return
     */
    String updateGoodsCategory(BooksCategory booksCategory);

    /**
     * 获取分类
     * @param id
     * @return
     */
    BooksCategory getBooksCategoryById(Long id);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<IndexCategoryVO> getCategoriesForIndex();

    /**
     * 返回分类数据(搜索页调用)
     *
     * @param categoryId
     * @return
     */
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<BooksCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
