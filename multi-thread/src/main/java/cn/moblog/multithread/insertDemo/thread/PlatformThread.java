package cn.moblog.multithread.insertDemo.thread;

import cn.moblog.multithread.insertDemo.mapper.UserMapper;
import cn.moblog.multithread.insertDemo.util.SpringUtils;
import cn.moblog.multithread.vo.PlatformAppsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName UserThread
 * @Description TODO
 * @Author pinux
 * @Date 19-3-31 上午2:21
 * @Version 1.0
 */
public class PlatformThread implements Runnable {
    Logger logger = LoggerFactory.getLogger(PlatformThread.class);

    CountDownLatch cdl;
    List<PlatformAppsUserService> userList;

    public PlatformThread(CountDownLatch cdl, List<PlatformAppsUserService> userList) {
        this.cdl = cdl;
        this.userList = userList;
    }

    @Override
    public void run() {
        UserMapper userMapper = (UserMapper) SpringUtils.getBean("userMapper");
        userMapper.insertBatchPlatform(userList);
        logger.info("插入成功，当前线程是:" + Thread.currentThread().getName());
        cdl.countDown();
    }
}