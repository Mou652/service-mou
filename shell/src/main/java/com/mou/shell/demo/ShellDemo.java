package com.mou.shell.demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author: mou
 * @date: 2019-09-03
 */
public class ShellDemo {


    public static void main(String[] args) {
//        String osName = System.getProperty("os.name");
//        String[] cmd = new String[3];
//
//        System.out.println(osName);
//        //不同平台上，命令的兼容性
//        if(osName.equals("Windows NT")) {
//            cmd[0] = "cmd.exe" ;
//            cmd[1] = "/C" ;
//            cmd[2] = args[0];
//        } else if(osName.equals("Windows 95")) {
//            cmd[0] = "command.com" ;
//            cmd[1] = "/C" ;
//            cmd[2] = args[0];
//        }
//        //TODO cmd为执行的命令参数

        // callScript("test.sh", "4", "/Users/mou/IdeaProjects/service-mou/shell/src/main/resources/shell/");
        String path = ShellDemo.class.getClassLoader().getResource("shell").getPath();
        callScript("test.sh", "4", path);
    }

    /**
     * 执行shell
     *
     * @param script    shell文件名称
     * @param args      参数
     * @param workspace sh脚本目录
     */
        private static void callScript(String script, String args, String... workspace) {
        try {
            String cmd = "ifconfig";
//            String[] cmd = {"sh", script, args};
            File dir = null;
            if (workspace[0] != null) {
                dir = new File(workspace[0]);
                System.out.println(workspace[0]);
            }
            //变量
            String[] evnp = {"val=2", "call=Bash Shell"};
            Process process = Runtime.getRuntime().exec(cmd, evnp, dir);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        String url="192.168.110.22:22/sys";
        String[] split = url.split("/");

    }
}
