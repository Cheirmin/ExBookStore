package com.cheirmin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:04
 * @Version 1.0
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    //跳转登陆页面
    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    //跳转主页
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        return "admin/index";
    }

    //完成一半的登陆
    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
//        String kaptchaCode = session.getAttribute("verifyCode") + "";
//        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
//            session.setAttribute("errorMsg", "验证码错误");
//            return "admin/login";
//        }

        if (1 == 1) {
            session.setAttribute("loginUser", "张三");
            session.setAttribute("loginUserId", 1);
            //session过期时间设置为3600秒 即一小时
            session.setMaxInactiveInterval(60 * 60 * 1);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登陆失败，请联系超级管理员获得测试账号");
            return "admin/login";
        }
    }
}
