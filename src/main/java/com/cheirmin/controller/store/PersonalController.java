package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.pojo.User;
import com.cheirmin.service.UserService;
import com.cheirmin.util.Result;
import com.cheirmin.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:12
 * @Version 1.0
 */
@Controller
public class PersonalController {

    @Resource
    private UserService userService;

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.USER_SESSION_KEY);
        return "store/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "store/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        return null;
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "store/register";
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("registerEmail") String registerEmail,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           @RequestParam("password1") String password1,
                           HttpSession httpSession) {
        Object verifyCode1 = httpSession.getAttribute("EmailVerifyCode".concat(registerEmail));
        if (StringUtils.isEmpty(verifyCode)) {
            //验证码为空
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        if (verifyCode1 == null){
            //验证码未发送
            return ResultGenerator.genFailResult(ServiceResultEnum.VERIFY_CODE_NOT_SEND.getResult());
        }
        String sessioncode = Integer.toString((Integer) verifyCode1);

        if (!verifyCode.equals(sessioncode)){
            //验证码不正确
            return ResultGenerator.genFailResult(ServiceResultEnum.VERIFY_CODE_NOT_TRUE.getResult());
        }

        if (StringUtils.isEmpty(registerEmail)) {
            //注册邮箱为空
            return ResultGenerator.genFailResult(ServiceResultEnum.REGISTER_EMAIL_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            //注册密码为空
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }

        //todo 清verifyCode
        String registerResult = userService.register(registerEmail, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "store/personal";
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody User user, HttpSession httpSession) {
       return null;
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

}
