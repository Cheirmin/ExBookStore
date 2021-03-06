package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.common.IndexConfigTypeEnum;
import com.cheirmin.vo.IndexCategoryVO;
import com.cheirmin.vo.IndexConfigBooksVO;
import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.service.CarouselService;
import com.cheirmin.service.CategoryService;
import com.cheirmin.service.IndexConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:主页控制
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:41
 * @Version 1.0
 */
@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private CarouselService carouselService;

    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<IndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        List<IndexCarousel> indexCarousels = carouselService.queryCarouselBySort();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/500";
        }

        List<IndexConfigBooksVO> hotBookses = indexConfigService.getConfigBooksesForIndex(IndexConfigTypeEnum.INDEX_BOOKS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<IndexConfigBooksVO> newBookses = indexConfigService.getConfigBooksesForIndex(IndexConfigTypeEnum.INDEX_BOOKS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<IndexConfigBooksVO> recommendBookses = indexConfigService.getConfigBooksesForIndex(IndexConfigTypeEnum.INDEX_BOOKS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);

        //新品
        request.setAttribute("newBookses", newBookses);
        //推荐商品
        request.setAttribute("recommendBookses", recommendBookses);
        //热销书籍
        request.setAttribute("hotBookses", hotBookses);
        //分类数据
        request.setAttribute("categories", categories);
        //轮播图
        request.setAttribute("carousels",indexCarousels);
        return "store/index";
    }

    @RequestMapping("/loadhot")
    @ResponseBody
    public ResponseEntity<List<IndexConfig>> loadhot(){
        List<IndexConfig> indexConfigs = indexConfigService.queryIndexConfig();
        return  ResponseEntity.ok(indexConfigs);
    }
}
