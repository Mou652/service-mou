// package cn.mou.mail;
//
// import cn.hutool.core.collection.CollUtil;
// import cn.hutool.core.util.StrUtil;
// import com.alibaba.fastjson.JSONObject;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Component;
// import tech.scedu.workbench.dto.MailDTO;
// import tech.scedu.workbench.util.userBase.MailService;
//
// import javax.annotation.Resource;
// import javax.mail.MessagingException;
// import javax.mail.internet.MimeMessage;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * 邮件发送实现类
//  *
//  * @author: mou
//  * @date: 2019/10/29
//  */
// @Component
// @Slf4j
// public class MailService  {
//
// 	@Resource
// 	private JavaMailSender mailSender;
// 	@Value ("${edu.mail.fromMail.addr}")
// 	private String addr;
//
// 	/**
// 	 * 异步发送文本邮件
// 	 *
// 	 * @param mailDTO 邮件dto
// 	 */
// 	@Async
// 	public void sendSimpleEmail(MailDTO mailDTO) {
// 	}
//
// 	/**
// 	 * 发送邮件验证码
// 	 *
// 	 * @param mailDTO
// 	 */
// 	@Async
// 	public void sendEmailCode(MailDTO mailDTO) {
// 		//创建模板邮件对象
// 		MimeMessage mimeMessage = mailSender.createMimeMessage();
// 		try {
// 			//true表示需要创建一个multipart message
// 			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
// 			helper.setTo(mailDTO.getToMail());
// 			helper.setFrom(addr);
// 			helper.setSubject(mailDTO.getTitle());
// 			helper.setText(templateEmailCode(mailDTO), true);
// 		} catch (MessagingException e) {
// 			log.error("发送邮件时发生异常,参数:{}", JSONObject.toJSON(mailDTO), e);
// 		}
// 		mailSender.send(mimeMessage);
// 	}
//
// 	/**
// 	 * 异步发送html邮件
// 	 *
// 	 * @param mailDTO 邮件dto
// 	 */
// 	@Async
// 	public void sendHtmlEmail(MailDTO mailDTO) {
// 		//创建模板邮件对象
// 		MimeMessage mimeMessage = mailSender.createMimeMessage();
// 		try {
// 			//true表示需要创建一个multipart message
// 			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
// 			helper.setFrom(addr);
// 			helper.setTo(mailDTO.getToMail());
// 			helper.setSubject(mailDTO.getTitle());
// 			helper.setText(template(mailDTO), true);
// 		} catch (MessagingException e) {
// 			log.error("发送邮件时发生异常,参数:{}", JSONObject.toJSON(mailDTO), e);
// 		}
// 		mailSender.send(mimeMessage);
// 	}
//
// 	/**
// 	 * 拼接通知的接口,格式:名称+注释,名称+注释(多个接口逗号隔开)
// 	 *
// 	 * @param mapList
// 	 * @return
// 	 */
// 	public String jointNameAndNote(List<Map<String, String>> mapList) {
// 		StringBuilder strBuilder = StrUtil.builder();
// 		if (CollUtil.isNotEmpty(mapList)) {
// 			for (int i = 0; i < mapList.size(); i++) {
// 				String name = mapList.get(i).get("name");
// 				String note = mapList.get(i).get("note");
// 				if (mapList.size() - 1 == i) {
// 					strBuilder.append(name).append("(").append(note).append(")");
// 				} else {
// 					strBuilder.append(name).append("(").append(note).append(")").append(",");
// 				}
// 			}
//
// 		}
// 		return strBuilder.toString();
// 	}
//
// 	/**
// 	 * 拼接发送邮件的内容
// 	 *
// 	 * @param mailDTO 邮件dto
// 	 * @return
// 	 */
// 	private String template(MailDTO mailDTO) {
// 		return "<html>\n" +
// 			"<body>\n" +
// 			"尊敬的用户:\n" +
// 			"<p style=\"text-indent: 2em\"> 您好,<span style=\"color: #ff454b\">" + mailDTO.getApiNameAndApiNote() + "</span>接口<span style=\"color: #ff454b\">" + mailDTO.getNotice() + "</span>,请前往 <b>" + mailDTO.getSite() + "</b> 查看详情!</p>\n" +
// 			"</body>\n" +
// 			"</html>";
// 	}
//
// 	private String templateEmailCode(MailDTO mailDTO) {
// 		return "<html>\n" +
// 			"<body>尊敬的用户:\n" +
// 			"<p style=\\\"text-indent: 2em\\\">"+mailDTO.getSite()+" 您的验证码是:<span style='color:blue'><u>" + mailDTO.getCode() + "</u></span>有效时长是2分钟，请勿向他人泄露此验证码。\n" +
// 			"</body>\n" +
// 			"</html>";
// 	}
// }
