package com.cheirmin.controller.store.retrieve;

import com.cheirmin.common.Constants;
import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.RetrieveBook;
import com.cheirmin.service.RetrieveBookService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.IsbnUtil;
import com.cheirmin.vo.BooksDetailVO;
import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

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
        BigDecimal divide = bookInfoByISBN.getOriginalPrice().divide(BigDecimal.valueOf(10));
        if (divide==null || divide.compareTo(BigDecimal.valueOf(0))==0){
            request.setAttribute("booksMessage", "抱歉，该书暂未查询到加个，请试试其他书籍吧！！");
            return "store/retrieve/message";
        }
        bookInfoByISBN.setSellingPrice(divide);

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
        if (retrieveBook.getBookIntro()!=null && retrieveBook.getBookIntro().length()>200){
            retrieveBook.setBookIntro(retrieveBook.getBookIntro().substring(0,200).concat("..."));
        }

        BooksDetailVO booksDetailVO = new BooksDetailVO();
        BeanUtil.copyProperties(retrieveBook, booksDetailVO);

        request.setAttribute("booksDetail", booksDetailVO);

        return "store/retrieve/detail";
    }

    @GetMapping("/myCart")
    public String myCart(HttpServletRequest request, HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        int booksTotal = 0;
        BigDecimal priceTotal = new BigDecimal(0);
        List<ShoppingCartItemVO> myShoppingCartItems = retrieveBookService.getMyCartItems(userVO.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)){
            //订单项总数
            booksTotal = myShoppingCartItems.stream().mapToInt(ShoppingCartItemVO::getBookCount).sum();

            if (booksTotal < 1){
                return "error/500";
            }
            //总价
            for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems){
                priceTotal = priceTotal.add(shoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(shoppingCartItemVO.getBookCount())));
            }
            if (priceTotal.compareTo(BigDecimal.valueOf(1)) == -1){
                return "error/500";
            }
        }
        request.setAttribute("booksTotal",booksTotal);
        request.setAttribute("priceTotal",priceTotal);
        request.setAttribute("myShoppingCartItems",myShoppingCartItems);
        return "store/retrieve/mycart";
    }
}
