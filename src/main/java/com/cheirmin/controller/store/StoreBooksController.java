package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.controller.vo.BooksDetailVO;
import com.cheirmin.controller.vo.SearchPageCategoryVO;
import com.cheirmin.pojo.Book;
import com.cheirmin.service.BooksService;
import com.cheirmin.service.CategoryService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Message:书本控制器
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:12
 * @Version 1.0
 */
@Controller
public class StoreBooksController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private BooksService booksService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.BOOKS_SEARCH_PAGE_LIMIT );
        //封装分类数据
        if (params.containsKey("booksCategoryId") && !StringUtils.isEmpty(params.get("booksCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("booksCategoryId") + "");

            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("booksCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", booksService.searchBookInfo(pageUtil));
        return "store/search";
    }

    @GetMapping("/book/detail/{booksId}")
    public String detailPage(@PathVariable("booksId") Long booksId, HttpServletRequest request) {
        if (booksId < 1) {
            return "error/error_5xx";
        }
        Book book = booksService.getBookById(booksId);
        if (book == null) {
            return "error/error_404";
        }
        BooksDetailVO booksDetailVO = new BooksDetailVO();
        BeanUtil.copyProperties(book, booksDetailVO);
        booksDetailVO.setBookCarouselList(book.getBookCarousel().split(","));
        request.setAttribute("booksDetail", booksDetailVO);
        return "store/detail";
    }
}
