package com.cheirmin.controller.admin;

import com.cheirmin.common.CategoryLevelEnum;
import com.cheirmin.pojo.BooksCategory;
import com.cheirmin.service.BooksService;
import com.cheirmin.service.CategoryService;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.Result;
import com.cheirmin.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class BooksController {

    @Resource
    private BooksService booksService;
    @Resource
    private CategoryService categoryService;

    @GetMapping("/books")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "exbookstore_books");
        return "admin/exbookstore_books";
    }

    @GetMapping("/books/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<BooksCategory> firstLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), CategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<BooksCategory> secondLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<BooksCategory> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/exbookstore_books_edit";
            }
        }
        return "error/error_5xx";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/books/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);

        return ResultGenerator.genSuccessResult(booksService.getBooksPage(pageUtil));
    }
}
