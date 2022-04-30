package cn.mou.demo;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream API学习 参考:https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/index.html
 *
 * @author: 牟江川
 * @date: 2020/1/31
 */
public class StreamDemoTest {

    /*
        Stream是什么?
            Stream 不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的 Iterator。
            Stream 就如同一个迭代器（Iterator），单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。

        为什么需要Stream?
            Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），
            或者大批量数据操作 (bulk data operation)。Stream API 借助于同样新出现的 Lambda 表达式，极大的提高编程效率和程序可读性。
            同时它提供串行和并行两种模式进行汇聚操作，并发模式能够充分利用多核处理器的优势，使用 fork/join 并行方式来拆分任务和加速处理过程。
            通常编写并行代码很难而且容易出错, 但使用 Stream API 无需编写一行多线程的代码，就可以很方便地写出高性能的并发程序。
     */

    private Stream stream = Stream.of("a", "b", "c");

    /**
     * 流的构造与转换
     * 构造流的几种常见方法
     */
    @Test
    public void test1() {
        // 1. Individual values
        stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        //需要注意的是，对于基本数值型，目前有三种对应的包装类型 Stream：
        //数值流的构造
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * 流转换为其它数据结构
     */
    @Test
    public void test2() {
        //一个 Stream 只可以使用一次，上面的代码为了简洁而重复使用了数次。

        // 1. Array
        String[] strArray1 = (String[]) stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = (List<String>) stream.collect(Collectors.toList());
        List<String> list2 = (List<String>) stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = (Set) stream.collect(Collectors.toSet());
        Stack stack1 = (Stack) stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();
    }

    /**
     * map
     */
    @Test
    public void test3() {
        //这段代码把所有的单词转换为大写。
        ArrayList<String> wordsList = Lists.newArrayList("a", "b", "c");
        List<String> newWordsList = wordsList.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(newWordsList);

        //生成一个整数 list 的平方数
        ArrayList<Integer> squareList = Lists.newArrayList(2, 4, 8);
        List<Integer> newSquareList = squareList.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(newSquareList);
    }

    /**
     * flatMap
     * 从上面例子可以看出，map 生成的是个 1:1 映射，每个输入元素，都按照规则转换成为另外一个元素。
     * 还有一些场景，是一对多映射关系的，这时需要 flatMap。
     * 将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字。
     */
    @Test
    public void test4() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
    }

    /**
     * filter
     * 过滤掉不符合条件的元素,对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。
     */
    @Test
    public void test5() {
        //过滤掉偶数
        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.out.println(Joiner.on(",").join(evens));
    }

    /**
     * forEach
     * 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
     */
    @Test
    public void test6() {
        //forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。
        //terminal 操作，因此它执行后，Stream 的元素就被“消费”掉了，你无法对一个 Stream 进行两次 terminal 运算
        stream.forEach(element -> System.out.println(element));
        stream.forEach(element -> System.out.println(element));

        //peek 对每个元素执行操作并返回一个新的 Stream
        //相当于一个for循环,对循环内的元素进行两次操作(peek)
        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());
    }

    /**
     * findFirst
     * 这是一个 termimal 兼 short-circuiting 操作，它总是返回 Stream 的第一个元素，或者空。
     * 这里比较重点的是它的返回值类型：Optional。这也是一个模仿 Scala 语言中的概念，作为一个容器，
     * 它可能含有某值，或者不包含。使用它的目的是尽可能避免 NullPointerException。
     */
    @Test
    public void test7() {
        String strA = " abcd ", strB = null;
        print(strA);
        print("");
        print(strB);
        getLength(strA);
        getLength("");
        getLength(strB);

    }

    /**
     * reduce
     * 这个方法的主要作用是把 Stream 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），
     * 和前面 Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。
     */
    @Test
    public void test8() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        //写法一
        Integer sum = Stream.of(1, 2, 3).reduce(0, (a, b) -> a + b);
        //写法二
        sum = Stream.of(1, 2, 3).reduce(0, Integer::sum);
        System.out.println(sum);

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        /*
            上面代码例如第一个示例的 reduce()，第一个参数（空白字符）即为起始值，第二个参数（String::concat）为 BinaryOperator。
            这类有起始值的 reduce() 都返回具体的对象。而对于第四个示例没有起始值的 reduce()，由于可能没有足够的元素，返回的是 Optional，
            请留意这个区别。
         */
    }

    /**
     * limit/skip
     * limit 返回 Stream 的前面 n 个元素；
     * skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）。
     */
    @Test
    public void test9() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i, 10);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
        /*
            这是一个有 10，000 个元素的 Stream，但在 short-circuiting 操作 limit 和 skip 的作用下，管道中 map 操作指定的
            getName() 方法的执行次数为 limit 所限定的 10 次，而最终返回结果在跳过前 3 个元素后只有后面 7 个返回。
         */

    }

    /**
     * sorted
     * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类 map、filter、limit、skip 甚至 distinct
     * 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间。
     */
    @Test
    public void test10() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i, 10);
            persons.add(person);
        }
        //写法一
        List<Person> personList2 = persons.stream().limit(2).sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
        //写法二
        personList2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * min/max/distinct
     * min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为 O(n)，而 sorted 的成本是 O(n log n)。
     * 同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
     */
    @Test
    public void test11() throws IOException {
        //找出最长一行的长度
        BufferedReader br = new BufferedReader(new FileReader("/Applications/SUService.log"));
        int longest = br.lines().
                mapToInt(String::length).
                max().
                getAsInt();
        br.close();
        System.out.println(longest);

        //找出全文的单词，转小写，并排序
        List<String> words = br.lines().
                flatMap(line -> Stream.of(line.split(" ")))
                .filter(word -> word.length() > 0)
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        br.close();
        System.out.println(words);
    }

    /**
     * Match
     * Stream 有三个 match 方法，从语义上说：
     * <p>
     * allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     */
    @Test
    public void test12() {
        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = persons.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);
    }


    /*
     * 进阶：自己生成流
     * 通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的 Stream。
     * 把 Supplier 实例传递给 Stream.generate() 生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。由于它是无限的，
     * 在管道中，必须利用 limit 之类的操作限制 Stream 大小。
     */

    /**
     * Stream.generate
     */
    @Test
    public void test13() {
        //生成 10 个随机整数
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);
        /*
            Stream.generate() 还接受自己实现的 Supplier。例如在构造海量测试数据的时候，用某种自动的规则给每一个变量赋值；
            或者依据公式计算 Stream 的每个元素值。这些都是维持状态信息的情形。
         */

        //自实现 Supplier
        Stream.generate(new PersonSupplier()).
                limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
    }

    /**
     * Stream.iterate
     * <p>
     * iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。然后种子值成为 Stream 的第一个元素，
     * f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
     */
    @Test
    public void test14() {
        //生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));

        /*
            与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小。
         */
    }


    /*
       进阶: 用 Collectors 来进行 reduction 操作
       java.util.stream.Collectors 类的主要作用就是辅助进行各类有用的 reduction 操作，例如转变输出为 Collection，把 Stream 元素进行归组。
     */

    /**
     * groupingBy/partitioningBy
     */
    @Test
    public void test15() {
        //按照年龄归组
        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }

        //按照未成年人和成年人归组
        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());

        /*
            在使用条件“年龄小于 18”进行分组后可以看到，不到 18 岁的未成年人是一组，成年人是另外一组。partitioningBy 其实是一种特殊的 groupingBy，
            它依照条件测试的是否两种结果来构造返回的数据结构，get(true) 和 get(false) 能即为全部的元素对象。
         */

    }

    /**
     * test7调用
     *
     * @param text
     */
    public void print(String text) {
        // Java 8
        Optional.ofNullable(text).ifPresent(System.out::println);
        // Pre-Java 8
        if (text != null) {
            System.out.println(text);
        }
    }

    /**
     * test7调用
     *
     * @param text
     */
    public int getLength(String text) {
        // Java 8
        return Optional.ofNullable(text).map(String::length).orElse(-1);
        // Pre-Java 8
        // return if (text != null) ? text.length() : -1;
    }

    /**
     * test9调用
     */
    @Data
    class Person {
        public int age;
        private String name;

        public Person(int age, String name, int i) {
            this.age = age;
            this.name = name;
        }

        public String getName() {
            System.out.println(name);
            return name;
        }
    }

    /**
     * test13调用
     */
    private class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }

    /*
           结束语:
                总之，Stream 的特性可以归纳为：
                不是数据结构。
                它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
                它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
                所有 Stream 的操作必须以 lambda 表达式为参数
                不支持索引访问
                你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
                很容易生成数组或者 List
                惰性化
                很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
                Intermediate 操作永远是惰性化的。
                并行能力
                当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
                可以是无限的
                集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
     */
}

