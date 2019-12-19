package com.mou.email;

import com.mou.email.demo.MailDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.fromMail.addr}")
    private String addr;
    @Resource
    private JavaMailSender mailSender;


    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("这是一封测试邮件");
        message.setFrom(addr);
        message.setTo("847474659@qq.com");
        message.setSentDate(new Date());
        message.setText("雍卓斯基,你好");
        try {
            javaMailSender.send(message);
            System.out.println("发送成功");
        } catch (MailException e) {
            System.out.println("发送失败");
            e.printStackTrace();

        }
    }

    @Test
    public void test() {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setCustomerName("周杰伦");
        mailDTO.setToMail("847474659@qq.com");
        mailDTO.setApiNameAndApiNote("CXXSLB(查询学生列表)");
        mailDTO.setNotice("已授权");
        //创建模板邮件对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(addr);
            helper.setTo(mailDTO.getToMail());
            helper.setSubject("服务平台-接口通知");
            helper.setText(template(mailDTO), true);

        } catch (MessagingException e) {
//            log.error("发送邮件时发生异常！", e);
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 拼接发送邮件的内容
     *
     * @param mailDTO 邮件dto
     * @return
     */
    private String template(MailDTO mailDTO) {
        return "<html>\n" +
                "<body>\n" +
                "<h3>" + mailDTO.getCustomerName() + "客户:</h3>\n" +
                "<p style=\"text-indent: 2em\"> 您好," + mailDTO.getApiNameAndApiNote() + "接口" + mailDTO.getNotice() + ",可前往" + mailDTO.getSite() + "</p>\n" +
                "</body>\n" +
                "</html>";
    }

}
