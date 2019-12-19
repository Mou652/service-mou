package com.mou.email.demo;

import lombok.Data;

/**
 * 邮件dto
 *
 * @author: mou
 * @date: 2019/10/29
 */
@Data
public class MailDTO {

    private String toMail;

    private String customerName;

    private String apiNameAndApiNote;

    private String notice;

    private String site = "XXXXXXXX";
}
