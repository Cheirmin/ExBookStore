package com.cheirmin.service.impl;

import com.cheirmin.dao.BookMapper;
import com.cheirmin.pojo.Book;
import com.cheirmin.service.BooksService;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/20 18:00
 * @Version 1.0
 */
@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public PageResult getBooksPage(PageQueryUtil pageUtil) {

        Example example=new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andIsNotNull("bookId");

        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<Book> goodsList = bookMapper.selectByExampleAndRowBounds(example,rowBounds);

        int total = bookMapper.selectCountByExample(example);

        return new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public String saveBookInfo(Book book) {
        return null;
    }

    @Override
    public void batchSaveBooks(List<Book> bookList) {

    }

    @Override
    public String updateBookInfo(Book book) {
        return null;
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return null;
    }

    @Override
    public PageResult searchBookInfo(PageQueryUtil pageUtil) {
        return null;
    }
}
