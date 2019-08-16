package com.mou.quartz.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.logging.Logger;

/**
 * 自定义任务
 *
 * @author: mou
 * @date: 2019-08-12
 */
public class ScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行自定义任务");
    }
}
