package com.mou.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();

//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        sf.setTrustAllHosts(true);

        message.setSubject("这是一封测试邮件");
        message.setFrom("847474659@qq.com");
        message.setTo("1508679710@qq.com");
        message.setSentDate(new Date());
        message.setText("雍卓斯基,你好");
        javaMailSender.send(message);
    }

}
