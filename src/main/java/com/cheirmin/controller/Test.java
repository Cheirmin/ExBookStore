package com.cheirmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 16:32
 * @Version 1.0
 */
@Controller
public class Test {

    @GetMapping("fast")
    public String fast(String str){
        System.out.println("--fast--");
        return str;
    }
}
