package cn.moblog.multithread.utils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 并行处理任务工具类
 * <p>
 * TaskDisposeUtils是一个并行处理的工具类，可以传入n个任务内部使用线程池进行处理，等待所有任务都处理完成之后
 * ,方法才会返回。比如我们发送短信，系统中有1万条短信，我们使用上面的工具，每次取100条并行发送，待100个都处理完毕之后
 * ,再取一批按照同样的逻辑发送。
 *
 * @author: mou
 * @date: 2020/1/31
 */
public class TaskDisposeUtils {
    /**
     * 并行线程数
     */
    public static int POOL_SIZE = 0;

    static {
        /*
            Runtime.getRuntime().availableProcessors()
            Java虚拟机返回可用处理器的数目。
         */
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }

    /**
     * 并行处理,并等待结束
     *
     * @param taskList 任务列表
     * @param consumer 消费者
     * @param <T>
     * @throws InterruptedException
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        dispose(true, POOL_SIZE, taskList, consumer);
    }

    /**
     * 重载方法,并行处理,并等待结束
     *
     * @param moreThread 是否需要多线程执行
     * @param poolSize   线程池大小
     * @param taskList   任务列表
     * @param consumer   消费者
     * @param <T>
     */
    private static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) throws InterruptedException {
        if (taskList == null || taskList.isEmpty()) {
            return;
        }
        //多线程执行
        if (moreThread && poolSize > 1) {
            poolSize = Integer.min(poolSize, taskList.size());
            ExecutorService executorService = null;
            try {
                executorService = Executors.newFixedThreadPool(poolSize);
                CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
                for (T item : taskList) {
                    executorService.submit(() -> {
                        try {
                            consumer.accept(item);
                        } finally {
                            countDownLatch.countDown();
                        }

                    });
                }
                countDownLatch.await();
            } finally {
                if (executorService != null) {
                    executorService.shutdown();
                }
            }
        } else {
            for (T item : taskList) {
                consumer.accept(item);
            }
        }
    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        /*
            Stream.iterate(1,a->a+1):生成一个等差数列(1,2,3,4,5)
            .limit(10),只截取前10的数字
         */

        //生成1-10的10个数字,放在list中,相当于10个任务
        List<Integer> list = Stream.iterate(1, n -> n + 1).limit(10).collect(Collectors.toList());
        TaskDisposeUtils.dispose(list, item -> {
            try {
                long startTime = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long endTime = System.currentTimeMillis();
                System.out.println(System.currentTimeMillis() + ",任务" + item + "执行完毕,耗时:" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(list + "中的任务执行完毕!");
    }
}
