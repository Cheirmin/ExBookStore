package com.cheirmin.service.impl;

import com.cheirmin.service.MailBizService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/14 13:43
 * @Version 1.0
 */
@Slf4j
@Service
public class MailBizServiceImpl implements MailBizService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     *发送邮件
     * @param subject 主题
     * @param body    内容
     * @param from    发件人
     * @param to      收件人[多个]
     * @throws Exception
     */
    @Override
    public void sendMail(String subject, String body, String from, String[] to) throws Exception {
        sendMail(subject, body, from, null, to);
    }

    /**
     * 发送邮件
     * @param subject 主题
     * @param body    内容
     * @param from    发件人
     * @param cc      抄送人[多个]
     * @param to      收件人[多个]
     * @throws Exception
     */
    @Override
    public void sendMail(String subject, String body, String from, String[] cc, String[] to) throws Exception {
        sendMail(subject, body, false, from, cc, to);
    }

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
    @Override
    public void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to) throws Exception {
        sendMail(subject, body, html, from, cc, to, null);
    }

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
    @Override
    public void sendMail(String subject, String body, boolean html, String from, String[] cc, String[] to, List<String> attachPathList) throws Exception {
        try {
            MimeMessage parentMimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(parentMimeMessage, true, "utf-8");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(new InternetAddress(from));
            mimeMessageHelper.setSentDate(new Date());

            // 设置收件人地址
            mimeMessageHelper.setTo(to);

            // 设置抄送人
            if (!StringUtils.isEmpty(cc)) {
                mimeMessageHelper.setCc(cc);
            }

            // 设置正文
            mimeMessageHelper.setText(body, html);

            // 设置附件
            if (!CollectionUtils.isEmpty(attachPathList)) {
                Multipart multipart = new MimeMultipart();
                for (String attachPath : attachPathList) {
                    MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    DataHandler dataHandler = new DataHandler(new FileDataSource(attachPath));
                    mimeBodyPart.setDataHandler(dataHandler);
                    mimeBodyPart.setFileName(dataHandler.getName());
                    multipart.addBodyPart(mimeBodyPart);
                }
                parentMimeMessage.setContent(multipart);
            }
            // 正式发送邮件
            javaMailSender.send(parentMimeMessage);
        } catch (Exception e) {
            System.out.println("发送邮件失败："+e);
            throw e;
        }
    }
}
