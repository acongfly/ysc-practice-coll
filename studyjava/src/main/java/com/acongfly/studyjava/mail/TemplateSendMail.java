package com.acongfly.studyjava.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * @program: ysc-practice-coll
 * @description:
 * @author: shicong yang
 * @create: 2019-09-06 16:22
 **/
@Service
public class TemplateSendMail {

    // @Autowired
    // private ;

    @Autowired
    private Configuration freemarkerConfiguration;

    public String freemarker() throws IOException, TemplateException, javax.mail.MessagingException {
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        MimeMessage message = javaMailSender.createMimeMessage();
        // 第二个参数表示是否开启multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom("test");
        messageHelper.setTo("511932633@qq.com");
        messageHelper.setSubject("基于freemarker模板的邮件测试");

        Map<String, Object> model = new HashMap<>();
        model.put("username", "itmuch");
        model.put("event", "IT牧场大事件");

        String content = FreeMarkerTemplateUtils
            .processTemplateIntoString(this.freemarkerConfiguration.getTemplate("TestMail.ftl"), model);

        System.out.println(content);
        // 第二个参数表示是否html，设为true
        messageHelper.setText(content, true);

        // this.javaMailSender.send(message);
        return "success";
    }
}
