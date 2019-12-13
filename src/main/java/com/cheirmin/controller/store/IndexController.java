package com.cheirmin.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {

        return "store/index";
    }
}
