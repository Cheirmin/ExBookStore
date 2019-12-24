package com.cheirmin.controller.admin;

import com.cheirmin.pojo.IndexConfig;
import com.cheirmin.service.IndexConfigService;
import com.cheirmin.util.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class BooksIndexConfigController {
    @Resource
    IndexConfigService indexConfigService;

     @GetMapping("/indexConfigs")
    public String loadindexConfigs(String configType, HttpServletRequest request){
         request.setAttribute("configType",configType);
         return "admin/exbookstore_index_config";
     }

     @GetMapping("/indexConfigs/list")
     @ResponseBody
    public  PageInfo loadindexConfigslist(Integer indexpage,Integer pagesize,String configType){
         PageInfo pageInfo = indexConfigService.queryallIndexConfig(indexpage, pagesize, configType);
         if (pageInfo!=null){
             return pageInfo;
         }
         return null;
     }

     @RequestMapping("/indexConfigs/save")
     @ResponseBody
    public Result addIndexConfig(@RequestBody IndexConfig indexConfig,HttpServletRequest request){
         boolean b = indexConfigService.addIndexConfig(indexConfig,request);
         if (b){
             Result result=new Result(200,"success");
             return result;
         }
         return null;
     }

    @RequestMapping("/indexConfigs/update")
    @ResponseBody
    public Result updateIndexConfig(@RequestBody IndexConfig indexConfig,HttpServletRequest request){
        System.out.println(indexConfig);
         boolean b = indexConfigService.updateIndexConfig(indexConfig,request);
        if (b){
            Result result=new Result(200,"success");
            return result;
        }
        return null;
    }

    //    用户点击删除，并批量修改字段is_deleted
    @RequestMapping("/indexConfigs/delete")
    public ResponseEntity updatecarousels(@RequestBody List<Long> ids, HttpServletRequest request){
        boolean b = indexConfigService.updateIndexConfigByids(ids,request);
        if (b){
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("failed");
    }

}
