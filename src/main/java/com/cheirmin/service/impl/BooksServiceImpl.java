package com.cheirmin.service.impl;

import com.cheirmin.vo.SearchBooksVO;
import com.cheirmin.mapper.BookMapper;
import com.cheirmin.pojo.Book;
import com.cheirmin.service.BooksService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
        return bookMapper.selectByPrimaryKey(id);
    }

//    批量更新
    @Override
    public Boolean batchUpdateSellStatus(List<Long> ids, int sellStatus, HttpServletRequest request) {

        if(StringUtils.isEmpty(ids))
            throw new RuntimeException("PARAMS ERROR");
        Example example2=new Example(Book.class);
        example2.createCriteria().andIn("bookId",ids);
        Book b = new Book();
        b.setBookSellStatus( ((Integer)sellStatus).byteValue() );
        int res = bookMapper.updateByExampleSelective(b, example2);
        return res > 0;
    }

    @Override
    public PageResult searchBookInfo(PageQueryUtil pageUtil) {

        String booksCategoryId = (String) pageUtil.get("booksCategoryId");
        Example example2=new Example(Book.class);
        Example.Criteria criteria2 = example2.createCriteria();
        if (booksCategoryId != null && !booksCategoryId.equals("")) {
            criteria2.orEqualTo("bookCategoryId1",booksCategoryId);
            criteria2.orEqualTo("bookCategoryId2",booksCategoryId);
            criteria2.orEqualTo("bookCategoryId3",booksCategoryId);
        }

        String keyword =  (String) pageUtil.get("keyword");

        if (keyword== null) keyword= "";
        keyword = "%" + keyword + "%";

        Example example=new Example(Book.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orLike("bookName",keyword);
        criteria.orLike("bookAbstract",keyword);

        example.and(criteria2);
        example.and(criteria);

        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<Book> goodsList = bookMapper.selectByExampleAndRowBounds(example,rowBounds);

        int total = bookMapper.selectCountByExample(example);
        List<SearchBooksVO> searchBooksVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            searchBooksVOS = BeanUtil.copyList(goodsList, SearchBooksVO.class);
            for (SearchBooksVO searchBooksVO : searchBooksVOS) {
                String bookName = searchBooksVO.getBookName();
                String bookIntro = searchBooksVO.getBookIntro();
                // 字符串过长导致文字超出的问题
                if (bookName.length() > 28) {
                    bookName = bookName.substring(0, 28) + "...";
                    searchBooksVO.setBookName(bookName);
                }
                if (bookIntro.length() > 30) {
                    bookIntro = bookIntro.substring(0, 30) + "...";
                    searchBooksVO.setBookIntro(bookIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(searchBooksVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
