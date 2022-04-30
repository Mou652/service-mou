package ecs.demo;

import java.math.BigDecimal;
import java.util.concurrent.atomic.DoubleAdder;

public class Demo {
    private static DoubleAdder doubleAdder = new DoubleAdder();

    class Super {
        int flag = 1;

        Super() {
            test();
        }

        void test() {
            System.out.println("Super.test() flag=" + flag);
        }
    }

    class Sub extends Super {
        Sub(int i) {
            flag = i;
            System.out.println("子类构造:Sub.Sub()flag=" + flag);
        }

        @Override
        void test() {
            System.out.println("Sub.test()flag=" + flag);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            doubleAdder.add(0.1);
        }
        System.out.println(doubleAdder.doubleValue());
        doubleAdder.reset();
        System.out.println(doubleAdder.doubleValue());

        System.out.println(BigDecimal.valueOf(5).subtract(BigDecimal.valueOf(3)).divide(BigDecimal.valueOf(2)));
    }

}
