package com.mou.shell.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ：dongbl
 * @version ：
 * @Description：
 * @date ：9:19 2017/11/14
 */
public class TestBash {
    public static void main(String [] args){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入IP:");
            String ip = reader.readLine();
            String bashCommand = "sh "+ "/usr/local/java/jdk1.8.0_121/lib/stopffmpeg.sh" + " ffmpeg " + ip;
//            String bashCommand = "chmod 777 " + "/usr/local/java/jdk1.8.0_121/lib/stopffmpeg.sh" ;
//            String bashCommand = "kill -9" + ip;
            System.out.println(bashCommand);
            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec(bashCommand);
            int status = pro.waitFor();
            if (status != 0)
            {
                System.out.println("Failed to call shell's command ");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringBuffer strbr = new StringBuffer();
            String line;            
            while ((line = br.readLine())!= null)
            {                
                strbr.append(line).append("\n");
            }

            String result = strbr.toString();
            System.out.println(result);

        }
        catch (IOException ec)
        {
            ec.printStackTrace();
        }
        catch (InterruptedException ex){
            ex.printStackTrace();

        }
    }
}