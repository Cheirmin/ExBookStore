package com.cheirmin.service;

import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.RetrieveBook;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: cheirmin/xiaoming.chen
 * @since: 2020/5/12 16:24
 * @history: 1.2020/5/12 created by cheirmin/xiaoming.chen
 */
public interface RetrieveBookService {
    /**
     * 新增二手书籍
     * @param retrieveBook
     * @return
     */
    Long addBook(RetrieveBook retrieveBook);
}
