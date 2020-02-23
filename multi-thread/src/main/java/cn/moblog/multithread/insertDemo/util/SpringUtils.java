package cn.moblog.multithread.insertDemo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SpringUtils  implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public SpringUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext != null?applicationContext.getBean(beanName):null;
    }

}
