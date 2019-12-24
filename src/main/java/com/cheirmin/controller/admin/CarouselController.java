package com.cheirmin.controller.admin;

import com.cheirmin.pojo.IndexCarousel;
import com.cheirmin.service.CarouselService;
import com.cheirmin.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

//    当用户访问轮播图时，加载页面
    @RequestMapping("loadcarousels")
    public String loadcarousels(){
        return "admin/exbookstore_carousel";
    }

//    加载页面时，请求数据
    @RequestMapping("carousels")
    @ResponseBody
    public String loadCarouselsinfo(Integer limit, Integer page){
        PageInfo pageInfo = carouselService.queryallCarousel(page, limit);
        ObjectMapper objectMapper=new ObjectMapper();
        if (pageInfo!=null){
            try {
                String data = objectMapper.writeValueAsString(pageInfo);
                return data;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    用户点击增加时，增加轮播图配置---完成
    @RequestMapping("/carousels/save")
    @ResponseBody
    public Result addcarousels(@RequestBody IndexCarousel indexCarousel,HttpServletRequest request){
        System.out.println(indexCarousel);
        boolean b = carouselService.addCarousel(indexCarousel,request);
        if (b){
            Result result=new Result(200,"success");
            return result;
        }
        return null;
    }


//    用户修改图片时，先根据当前的id，查询相关信息---完成
    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public ResponseEntity selectone(@PathVariable("id") Integer id){
        IndexCarousel indexCarousel = carouselService.selectoneCarousel(id);
        if (indexCarousel!=null){
            return ResponseEntity.ok(indexCarousel);
        }
        return ResponseEntity.ok("failed");
    }


//    用户点击修改后，保存----完成
    @RequestMapping("/carousels/update")
    @ResponseBody
    public Result renewcarousels(@RequestBody IndexCarousel indexCarousel,HttpServletRequest request){
        System.out.println(indexCarousel);
        boolean b = carouselService.updateCarousel(indexCarousel,request);
        if (b){
            Result result=new Result(200,"success");
            return result;
        }
        return null;
    }


//用户点击删除，并批量删除
//    @RequestMapping("/carousels/delete")
//    public ResponseEntity deletecarousels(@RequestBody String ids){
//        boolean b = carouselService.deleteCarousel(ids);
//        if (b){
//            return ResponseEntity.ok("success");
//        }
//        return ResponseEntity.ok("failed");
//    }

//    用户点击删除，并批量修改字段is_deleted
    @RequestMapping("/carousels/delete")
    public ResponseEntity updatecarousels(@RequestBody List<Integer> ids, HttpServletRequest request){
        boolean b = carouselService.updateCarouselByids(ids,request);
        if (b){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("failed");
    }
}
