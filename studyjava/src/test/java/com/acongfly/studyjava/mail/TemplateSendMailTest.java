package com.acongfly.studyjava.mail;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import freemarker.template.TemplateException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateSendMailTest {

    @Resource
    private TemplateSendMail templateSendMail;

    @Test
    public void freemarker() {
        try {
            templateSendMail.freemarker();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}