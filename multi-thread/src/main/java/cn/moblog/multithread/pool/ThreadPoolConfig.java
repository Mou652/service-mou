package cn.moblog.multithread.pool;//package com.mou.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author: mou
 * @date: 2019/12/24
 */
@Configuration
public class ThreadPoolConfig {

	@Bean("threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor asyncPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1000);
		executor.setMaxPoolSize(2000);
		executor.setQueueCapacity(99999);
		executor.setKeepAliveSeconds(60);
		//当pool已经达到max size的时候，如何处理新任务,不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		executor.initialize();
		return executor;
	}

	@Bean("threadPoolExecutor")
	public ThreadPoolExecutor asyncPoolExecutor() {
		return new ThreadPoolExecutor(50, 200, 60,
			TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024), new CustomThreadFactory(),
			new ThreadPoolExecutor.AbortPolicy());
	}

	/**
	 * 自定义线程工厂
	 */
	private class CustomThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("mou");
			return t;
		}
	}
}
