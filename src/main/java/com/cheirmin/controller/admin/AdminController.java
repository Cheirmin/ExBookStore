package com.cheirmin.controller.admin;

import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.pojo.AdminUser;
import com.cheirmin.service.AdminUserService;
import com.cheirmin.util.CodecUtils;
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

    /**
     * 跳转登陆页面
     * @return
     */
    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    /**
     * 跳转主页
     * @param request
     * @return
     */
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        return "admin/index";
    }

    /**
     * 登陆
     * @param userName
     * @param password
     * @param verifyCode
     * @param session
     * @return
     */
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

        AdminUser loginUser = adminUserService.findAdminByUsername(userName);

        if(loginUser==null){
            session.setAttribute("errorMsg", "账号不存在，请联系超管获取账号");
            return "admin/login";
        }

        if(!CodecUtils.passwordConfirm(userName.concat(password),loginUser.getLoginPassword())){
            session.setAttribute("errorMsg", "密码错误，若忘记密码请联系超管");
            return "admin/login";
        }else {
            session.setAttribute("loginAdminUser", loginUser.getLoginUserName());
            String identity = loginUser.getSuperAdmin()==1?"超级管理员":"普通管理员";
            session.setAttribute("loginAdminIdentity", identity);
            session.setAttribute("loginAdminNickName", loginUser.getNickName());
            session.setAttribute("loginAdminId", loginUser.getAdminUserId());

            //session过期时间设置为3600秒 即一小时
            session.setMaxInactiveInterval(60 * 60 * 1);
            return "redirect:/admin/index";
        }
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginAdminId");
        request.getSession().removeAttribute("loginAdminUser");
        request.getSession().removeAttribute("loginAdminIdentity");
        request.getSession().removeAttribute("loginAdminNickName");
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
        Integer loginUserId = (int) request.getSession().getAttribute("loginAdminId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginAdminUser", adminUser.getLoginUserName());
        request.setAttribute("loginAdminNickName", adminUser.getNickName());
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
        Integer loginUserId = (int) request.getSession().getAttribute("loginAdminId");


        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginAdminId");
            request.getSession().removeAttribute("loginAdminUser");
            request.getSession().removeAttribute("loginAdminIdentity");
            request.getSession().removeAttribute("loginAdminNickName");
            request.getSession().removeAttribute("errorMsg");
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "修改失败";
        }
    }

    /**
     * 修改个人信息
     * @param request
     * @param loginAdminUser
     * @param loginAdminNickName
     * @return
     */
    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginAdminUser") String loginAdminUser,
                                 @RequestParam("loginAdminNickName") String loginAdminNickName) {
        if (StringUtils.isEmpty(loginAdminUser) || StringUtils.isEmpty(loginAdminNickName)) {
            return "参数不能为空";
        }

        Integer loginUserId = (int) request.getSession().getAttribute("loginAdminId");

        if (adminUserService.updateAdmin(loginUserId,loginAdminUser,loginAdminNickName)){
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "修改失败";
        }
    }

}
