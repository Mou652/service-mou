package cn.moublog.spring.bean;

import cn.moublog.spring.annotation.XBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class XBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public XBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        super.addIncludeFilter(new AnnotationTypeFilter(XBean.class));
    }
}
