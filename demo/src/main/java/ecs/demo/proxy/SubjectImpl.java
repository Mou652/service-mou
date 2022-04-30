package ecs.demo.proxy;

/**
 * @author: mou
 * @date: 2021/2/27
 */
public class SubjectImpl implements Subject {

    @Override
    public String sayHello() {
        return "success";
    }
}
