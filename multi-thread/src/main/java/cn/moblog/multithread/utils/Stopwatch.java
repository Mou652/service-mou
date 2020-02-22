//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.moblog.multithread.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.Map;

public class Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(Stopwatch.class);
    private StopWatch sw = new StopWatch();
    private Map<String, Long> map = Maps.newLinkedHashMap();
    private long preTime = 0L;
    private boolean enabled = true;
    private Logger privateLogger = null;

    private Stopwatch() {
    }

    public static Stopwatch createAndBegin(boolean enabled) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.enabled = enabled;
        if (!enabled) {
            return stopwatch;
        } else {
            stopwatch.sw.start();
            return stopwatch;
        }
    }

    public static Stopwatch createAndBegin(Logger logger) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.privateLogger = logger;
        stopwatch.enabled = logger.isDebugEnabled();
        if (!stopwatch.enabled) {
            return stopwatch;
        } else {
            stopwatch.sw.start();
            return stopwatch;
        }
    }

    public void start() {
        if (this.enabled) {
            throw new RuntimeException("Stopwatch already started.");
        } else {
            this.enabled = true;
            this.sw.start();
        }
    }

    public void record(String tag) {
        if (this.enabled) {
            this.sw.split();
            long splitTime = this.sw.getSplitTime();
            long sub = splitTime - this.preTime;
            this.map.put(tag, sub);
            this.preTime = splitTime;
        }
    }

    private Map<String, Long> out() {
        if (this.enabled && !this.sw.isStopped()) {
            this.sw.stop();
            long all = this.sw.getTime();
            this.map.put("总计用时", all);
            return this.map;
        } else {
            return this.map;
        }
    }

    public void print(String tag) {
        this.print(Level.DEBUG, tag);
    }

    public void print(Level logLevel, String tag) {
        if (this.enabled) {
            switch (logLevel) {
                case DEBUG:
                    this.getLogger().debug(tag + " {}", this.out());
                    break;
                case INFO:
                    this.getLogger().info(tag + " {}", this.out());
                    break;
                case WARN:
                    this.getLogger().warn(tag + " {}", this.out());
                    break;
                case ERROR:
                    this.getLogger().error(tag + " {}", this.out());
                    break;
                default:
                    this.getLogger().trace(tag + " {}", this.out());
            }

        }
    }

    private Logger getLogger() {
        return privateLogger == null ? log : privateLogger;
    }
}
