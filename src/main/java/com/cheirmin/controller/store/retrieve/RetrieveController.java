package com.cheirmin.controller.store.retrieve;

import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.RetrieveBook;
import com.cheirmin.service.RetrieveBookService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.IsbnUtil;
import com.cheirmin.vo.BooksDetailVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2020/3/23 17:51
 * @Version 1.0
 */
@Controller
@RequestMapping("retrieveBook")
public class RetrieveController {

    @Resource
    private RetrieveBookService retrieveBookService;

    @GetMapping({"/index", "/", "/index.html",""})
    public String index(){
        return "store/retrieve/retrieve-cart";
    }

    @GetMapping("/retrieveBookDetail")
    public String bookDetail(HttpServletRequest request, @RequestParam("isbn")String isbn){

        //查询书籍为空
        if (isbn.trim()==null||isbn.trim().isEmpty()){
            request.setAttribute("booksMessage", "抱歉，您输入的ISBN为空！");
            return "store/retrieve/message";
        }

        Book bookInfoByISBN = IsbnUtil.getBookInfoByISBN(isbn);

        //查询书籍为空
        if (bookInfoByISBN == null){
            request.setAttribute("booksMessage", "抱歉，您输入的ISBN没有匹配到相关书籍！");
            return "store/retrieve/message";
        }

        //回收价
        bookInfoByISBN.setSellingPrice(bookInfoByISBN.getOriginalPrice().divide(BigDecimal.valueOf(10)));

        //存入数据库开始
        RetrieveBook retrieveBook = new RetrieveBook();
        BeanUtil.copyProperties(bookInfoByISBN, retrieveBook);

        Long id = retrieveBookService.addBook(retrieveBook);

        if (id==null || id==0){
            request.setAttribute("booksMessage", "抱歉，操作失败，请重试！！");
            return "store/retrieve/message";
        }
        //存入数据库结束

        //简介太长
        if (retrieveBook.getBookIntro().length()>200){
            retrieveBook.setBookIntro(retrieveBook.getBookIntro().substring(0,264).concat("..."));
        }

        BooksDetailVO booksDetailVO = new BooksDetailVO();
        BeanUtil.copyProperties(retrieveBook, booksDetailVO);

        request.setAttribute("booksDetail", booksDetailVO);

        return "store/retrieve/detail";
    }
}
