package com.cheirmin.controller;

import com.cheirmin.service.MailBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Message:测试控制器
 * @Author：Cheirmin
 * @Date: 2019/12/14 13:53
 * @Version 1.0
 */
@Controller
public class Test {
    @Autowired
    private MailBizService mailBizService;

    //发邮件测试
    @RequestMapping("/sendMail")
    public String sendMail(){
        try {
            //标题，内容，发送者，抄送人[多个]，收件人[多个]
            //具体方法可以去service里面看
            mailBizService.sendMail("测试-标题", "测试-body","1147905069@qq.com", new String[]{"1749984035@qq.com"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "邮件发送成功";
    }

    //测试方法，数据等，别提交了
    public static void main(String[] args) {
        List<String> strings =new ArrayList<>();
        strings.add("123");
        strings.add("456");

        String[] hah;

        hah = strings.toArray(new String[0]);

        for (String s : hah) {
            System.out.println(s);
        }
    }
}
