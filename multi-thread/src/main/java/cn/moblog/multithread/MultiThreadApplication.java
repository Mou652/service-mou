package cn.moblog.multithread;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("cn.moblog.multithread.insertDemo.mapper")
@SpringBootApplication
public class MultiThreadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiThreadApplication.class, args);
	}

}
