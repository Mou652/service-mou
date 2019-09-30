package com.mou.shell.demo;

import ch.ethz.ssh2.Connection;
import org.junit.Test;

/**
 * hutool工具类测试
 *
 * @author: mou
 * @date: 2019-09-04
 */
public class ShellDemo2 {

    /**
     * RemoteCommandUtils工具类测试
     */
    @Test
    public void test() {
        String username = "root";
        String password = "123456";
        String ip = "192.168.110.228";
        String port = "root";

        Connection conn = RemoteCommandUtil.login(ip, username, password);
        String top = RemoteCommandUtil.execute(conn, "sh test/test.sh");
//        String top2 = RemoteCommandUtil.execute(conn, "ifconfig");
        System.out.println(top);
//        System.out.println(top2);
    }
}
