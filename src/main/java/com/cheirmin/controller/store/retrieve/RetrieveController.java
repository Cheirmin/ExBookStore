package com.cheirmin.controller.store.retrieve;

import com.cheirmin.pojo.Book;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.IsbnUtil;
import com.cheirmin.vo.BooksDetailVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping({"/index", "/", "/index.html",""})
    public String index(){
        return "store/retrieve/retrieve-cart";
    }

    @GetMapping("inputISBN")
    public String inputISBN(){
        return "store/retrieve/input-isbn";
    }

    @GetMapping("/retrieveBookDetail")
    public String bookDetail(HttpServletRequest request, @RequestParam("isbn")String isbn){

        Book bookInfoByISBN = IsbnUtil.getBookInfoByISBN(isbn);

        if (bookInfoByISBN.getBookIntro().length()>200){
            bookInfoByISBN.setBookIntro(bookInfoByISBN.getBookIntro().substring(0,264).concat("..."));
        }

        bookInfoByISBN.setSellingPrice(bookInfoByISBN.getOriginalPrice().divide(BigDecimal.valueOf(10)));

        BooksDetailVO booksDetailVO = new BooksDetailVO();
        BeanUtil.copyProperties(bookInfoByISBN, booksDetailVO);

        request.setAttribute("booksDetail", booksDetailVO);

        return "store/retrieve/detail";
    }
}
