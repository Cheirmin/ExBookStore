package com.cheirmin.service;

import com.cheirmin.controller.vo.IndexCategoryVO;
import com.cheirmin.controller.vo.SearchPageCategoryVO;
import com.cheirmin.pojo.BooksCategory;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;

import java.util.List;

/**
 * @Message:
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

    String saveCategory(BooksCategory booksCategory);

    String updateGoodsCategory(BooksCategory booksCategory);

    BooksCategory getBooksCategoryById(Long id);

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
