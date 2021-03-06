package com.cheirmin.util;

import com.alibaba.fastjson.JSON;
import com.cheirmin.pojo.Book;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2020/3/23 16:51
 * @Version 1.0
 */
public class IsbnUtil {

    public static Book getBookInfoByISBN(String isbn){
        String url = "https://book.feelyou.top/isbn/"+isbn;

        System.out.println("isbn--"+ isbn);

        HttpUtil httpUtil = new HttpUtil();
        //获取响应体
        String body = httpUtil.GetHttp(url,1);

        System.out.println("body--"+body);

        if (body.length()<50){
            return null;
        }

        //json字符串转对象
        Map<String,Object> data = JSON.parseObject(body,Map.class);

        //查询出错
        if (data.get("error")!=null){
            return null;
        }

        String title = (String) data.get("title");
        String cover_url =  (String) data.get("cover_url");
        String _abstract = (String) data.get("abstract");

        String originalPrice = _abstract.substring(_abstract.lastIndexOf("/")+1).trim().replaceAll("元","");

        _abstract = _abstract.substring(0,_abstract.lastIndexOf("/")).trim();

        String book_intro = null;
        if(data.get("book_intro")!=null){
            book_intro = (String) data.get("book_intro");
        }else if(data.get("content")!=null){
            book_intro = (String) data.get("content");
        }

        Book book = new Book();
        book.setBookName(title);
        book.setOriginalPrice(BigDecimal.valueOf(Float.parseFloat(originalPrice)));

        book.setBookCoverImg(cover_url);
        book.setBookIsbn(isbn);
        book.setBookAbstract(_abstract);
        book.setBookIntro(book_intro);

        return book;
    }

}
