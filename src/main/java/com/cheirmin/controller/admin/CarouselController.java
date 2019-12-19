package com.cheirmin.controller.admin;

import com.cheirmin.service.CarouselService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:08
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class CarouselController {
    @Resource
    CarouselService carouselService;

    @RequestMapping("carousels")
    public String loadcarousels(Integer indexpage, Integer pagesize, HttpServletRequest request){
        PageInfo carousel = carouselService.queryallCarousel(indexpage, pagesize);
        System.out.println(carousel);
        request.setAttribute("carousel",carousel);
        return "admin/exbookstore_carousel";
    }

}
