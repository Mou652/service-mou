package cn.moublog.spring.annotation;

import cn.moublog.spring.bean.XBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义了一个扫描注解@XBeanScan。它有两个作用：
 *
 * 通过basePackages指定扫描包的范围。
 * 导入我们自定义ImportBeanDefinitionRegistrar 的实现XBeanDefinitionRegistrar。
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(XBeanDefinitionRegistrar.class)
public @interface XBeanScan {

    String[] basePackages();
}
