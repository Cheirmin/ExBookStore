package com.cheirmin.service;

import com.cheirmin.pojo.Book;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;

import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:37
 * @Version 1.0
 */

public interface BooksService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getBooksPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param book
     * @return
     */
    String saveBookInfo(Book book);

    /**
     * 批量新增商品数据
     *
     * @param bookList
     * @return
     */
    void batchSaveBooks(List<Book> bookList);

    /**
     * 修改商品信息
     *
     * @param book
     * @return
     */
    String updateBookInfo(Book book);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    Book getBookById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchBookInfo(PageQueryUtil pageUtil);

}
