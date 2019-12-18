package com.cheirmin.controller.common;

import com.cheirmin.service.MailBizService;
import com.cheirmin.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/14 12:08
 * @Version 1.0
 */
@Controller
@RequestMapping("email")
public class EmailController {
    @Autowired
    private MailBizService mailBizService;

    //从配置文件获取发件人信息
    @Value("${spring.mail.username}")
    private String mailUsername;

    @RequestMapping(value = "verifyCode", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public String sendEmail(@RequestBody Map<String, String> myMap, HttpServletRequest request){
        //生产6位随机验证码
        int code = NumberUtil.genRandomNum(6);

        //获取发送邮件参数
        String subject ="ExBookStore 旧生书店验证邮件";
        String body = "【旧生书店】验证码：" + code + "，请您完成验证，若非本人操作，请忽略该邮件。";
        String from = mailUsername;
        String[] to = {myMap.get("registerEmail")};

        //存入session
        request.getSession().setAttribute("EmailVerifyCode".concat(to[0]),code);
        System.out.println("6位随机码--" + code);

        try {
//            发送邮件已关闭，打开即可用
//            mailBizService.sendMail( subject, body, from, to);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "true";
    }
}
