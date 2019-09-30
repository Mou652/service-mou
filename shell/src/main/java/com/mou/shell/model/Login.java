package com.mou.shell.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: mou
 * @date: 2019-09-04
 */
@Data
@Component
@ConfigurationProperties(prefix = "linux")
public class Login {

    private String username;

    private String password;

    private String ip;

    private int port;
}
