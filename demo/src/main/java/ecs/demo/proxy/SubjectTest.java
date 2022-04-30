package ecs.demo.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author: mou
 * @date: 2021/2/27
 */
public class SubjectTest {

    @Test
    public void test() {
        Subject subject = new SubjectImpl();
        Subject proxy = (Subject)Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new ProxyInvocationHandler(subject));
        proxy.sayHello();
    }

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        Subject proxy = (Subject)Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new ProxyInvocationHandler(subject));
        proxy.sayHello();
    }
}
