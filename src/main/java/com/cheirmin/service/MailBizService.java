package com.cheirmin.service;

import java.util.List;

/**
 * @Message:邮箱工具接口,都是发邮件
 * @Author：Cheirmin
 * @Date: 2019/12/14 13:42
 * @Version 1.0
 */

public interface MailBizService {
    /**
     *发送邮件
     * @param subject 主题
     * @param body    内容
     * @param from    发件人
     * @param to      收件人[多个]
     * @throws Exception
     */
    void sendMail(String subject, String body, String from, String[] to) throws Exception;

    /**
     * 发送邮件
     * @param subject 主题
     * @param body    内容
     * @param from    发件人
     * @param cc      抄送人[多个]
     * @param to      收件人[多个]
     * @throws Exception
     */
    void sendMail(String subject, String body, String from, String[] cc, String[] to) throws Exception;

    /**
     * 发送邮件
     * @param subject 主题
     * @param body    内容
     * @param html    是否为html格式
     * @param from    发件人
     * @param cc      抄送人[多个]
     * @param to      收件人[多个]
     * @throws Exception
     */
    void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to) throws Exception;

    /**
     * 发送邮件
     * @param subject        主题
     * @param body           内容
     * @param html           是否为html格式
     * @param from           发件人
     * @param cc             抄送人[多个]
     * @param to             收件人[多个]
     * @param attachPathList 附件路径
     * @throws Exception
     */
    void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to, List<String> attachPathList) throws Exception;
}
