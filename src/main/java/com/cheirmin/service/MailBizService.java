package com.cheirmin.service;

import java.util.List;

/**
 * @Message:邮箱工具接口
 * @Author：Cheirmin
 * @Date: 2019/12/14 13:42
 * @Version 1.0
 */
public interface MailBizService {
    void sendMail(String subject, String body, String from, String[] to) throws Exception;

    void sendMail(String subject, String body, String from, String[] cc, String[] to) throws Exception;

    void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to) throws Exception;

    void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to, List<String> attachPathList) throws Exception;
}
