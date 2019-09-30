package com.mou.shell.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * @author: mou
 * @date: 2019-09-04
 */
@Slf4j
public class ShellDemo3 {

    private String host = "192.168.110.228";

    private String username = "root";

    private String password = "123456";

    @Test
    public void test() {
        //连接主机,执行shell命令
        String[] cmd={};
        Map<String, String> map = LinuxStateForShell.runDistanceShell(new String[]{"sh test/test.sh"}, username, password, host);
        System.out.println(map);

    }

}
