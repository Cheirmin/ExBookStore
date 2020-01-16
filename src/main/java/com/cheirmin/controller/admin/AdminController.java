package com.cheirmin.controller.admin;

import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.pojo.AdminUser;
import com.cheirmin.service.AdminUserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Message:管理员通用控制器
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:04
 * @Version 1.0
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

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

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }

    /**
     * 管理员个人信息展示
     * @param request
     * @return
     */
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    /**
     * 修改密码
     * @param request
     * @param originalPassword
     * @param newPassword
     * @return
     */
    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "修改失败";
        }
    }
}
