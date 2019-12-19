package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.controller.vo.IndexCategoryVO;
import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.service.CarouselService;
import com.cheirmin.service.CategoryService;
import com.cheirmin.service.IndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:41
 * @Version 1.0
 */
@Controller
public class IndexController {

//    @Resource
//    private CarouselService carouselService;
//
//    @Resource
//    private IndexConfigService indexConfigService;

    @Resource
    private CategoryService categoryService;

    @Resource
    CarouselService carouselService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<IndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        List<IndexCarousel> indexCarousels = carouselService.queryCarouselBySort();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/500";
        }

        if (CollectionUtils.isEmpty(indexCarousels)){
            return "error/500";
        }
        request.setAttribute("categories", categories);//分类数据
         request.setAttribute("carousels",indexCarousels);
        return "store/index";
    }
}
