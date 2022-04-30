package com.mou.strategy.single;

/**
 * 单例模式
 *
 * @author: mou
 * @date: 2020/3/1
 */
public class SingletonMode {


    /**
     * 不考虑并发访问的情况下标准的单例模式的构造方式,最标准也是最原始的单例模式的构造方式。
     * <p>
     * 1.静态实例，带有static关键字的属性在每一个类中都是唯一的。
     * 2.限制客户端随意创造实例，即私有化构造方法，此为保证单例的最重要的一步。
     * 3.给一个公共的获取实例的静态方法，注意，是静态的方法，因为这个方法是在我们未获取到实例的时候就要提供给客户端调用的，
     * 所以如果是非静态的话，那就变成一个矛盾体了，因为非静态的方法必须要拥有实例才可以调用。
     * 4.判断只有持有的静态实例为null时才调用构造方法创造一个实例，否则就直接返回。
     */
    static class Singleton {

        //一个静态的实例
        private static Singleton singleton;

        //私有化构造函数
        private Singleton() {
        }

        //给出一个公共的静态方法返回一个单一实例(未考虑并发情况)
        public static Singleton getInstance() {
            //     if (singleton == null) {
            //         singleton = new Singleton();
            //     }
            //     return singleton;
            // }

            /**
             * 教科书中标准的单例模式版本，也称为双重加锁。
             *
             * 深入到JVM中去探索还是问题:
             * 因为虚拟机在执行创建实例的这一步操作的时候，其实是分了好几步去进行的，也就是说创建一个新的对象并非是原子性操作。
             * 在有些JVM中上述做法是没有问题的，但是有些情况下是会造成莫名的错误。
             *   首先要明白在JVM创建新的对象时，主要要经过三步。
             *   1.分配内存
             *   2.初始化构造器
             *   3.将对象指向分配的内存的地址
             * 这种顺序在上述双重加锁的方式是没有问题的，因为这种情况下JVM是完成了整个对象的构造才将内存的地址交给了对象。
             * 但是如果2和3步骤是相反的（2和3可能是相反的是因为JVM会针对字节码进行调优，而其中的一项调优便是调整指令的执行顺序），
             * 就会出现问题了。因为这时将会先将内存地址赋给对象，针对上述的双重加锁，就是说先将分配好的内存地址指给synchronizedSingleton，
             * 然后再进行初始化构造器，这时候后面的线程去请求getInstance方法时，会认为synchronizedSingleton对象已经实例化了，直接返回一个引用。
             * 如果在初始化构造器之前，这个线程使用了synchronizedSingleton，就会产生莫名的错误。
             */
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }

    /**
     * 比较标准的单例模式,可以避免上面所说的问题
     * 因为一个类的静态属性只会在第一次加载类时初始化，这是JVM帮我们保证的，所以我们无需担心并发访问的问题。
     * 所以在初始化进行一半的时候，别的线程是无法使用的，因为JVM会帮我们强行同步这个过程。另外由于静态变量只初始化一次，
     * 所以singleton仍然是单例的。
     */
    public static Singleton getInstance1() {
        return SingletonInstance.instance;
    }

    static class SingletonInstance {

        static Singleton instance = new Singleton();

    }

    /**
     * 饿汉式加载
     * <p>
     * 上述方式与我们最后一种给出的方式类似，只不过没有经过内部类处理，这种方式最主要的缺点就是一旦我访问了Singleton的任何其他的静态域，
     * 就会造成实例的初始化，而事实是可能我们从始至终就没有使用这个实例，造成内存的浪费。不过在有些时候，直接初始化单例的实例也无伤大雅，
     * 对项目几乎没什么影响，比如我们在应用启动时就需要加载的配置文件等，就可以采取这种方式去保证单例。
     */
    private static Singleton singleton = new Singleton();

    // private Singleton(){}

    public static Singleton getInstance2() {
        return singleton;
    }

}