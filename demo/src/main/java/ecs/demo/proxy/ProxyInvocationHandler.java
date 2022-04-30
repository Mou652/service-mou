package ecs.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: mou
 * @date: 2021/2/27
 */
public class ProxyInvocationHandler implements InvocationHandler {

    private Object object;

    public ProxyInvocationHandler(Object target) {
        this.object = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理调用处理器");
        return method.invoke(proxy, args);
    }
}
