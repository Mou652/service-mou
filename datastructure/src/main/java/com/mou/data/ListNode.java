package com.mou.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 链表数据结构
 *
 * @author: mou
 * @date: 2021-04-03 09:04:48
 */
public class ListNode<E> {

    /**
     * 节点数据
     */
    E val;

    /**
     * 引用下一个节点的对象
     */
    ListNode<E> next;

    public ListNode() {
    }

    public ListNode(E val) {
        this.val = val;
    }

    public ListNode(E val, ListNode<E> next) {
        this.val = val;
        this.next = next;
    }

    /**
     * 获取节点
     *
     * @param node 链表
     * @return 链表内的节点
     */
    public List<E> getNode(ListNode<E> node) {
        List<E> resultNode = new ArrayList<>();
        while (node != null) {
            resultNode.add(node.val);
            node = node.next;
        }
        return resultNode;
    }

    /**
     * 内部测试类
     * 创建链表及遍历
     */
    static class ListNodeTest {
        public static void main(String[] args) {
            // 创建链表
            ListNode<Integer> firstNode = new ListNode<>(0);
            // 声明一个变量用来在移动过程中指向当前节点
            ListNode<Integer> nextNode;
            // 指向首节点
            nextNode = firstNode;

            for (int i = 1; i <= 5; i++) {
                // 生成新的节点,连接新节点
                nextNode.next = new ListNode<>(i);
                // 将节点往后移动
                nextNode = nextNode.next;
            }

            // 循环完成之后 nextNode指向最后一个节点,重新赋值让它指向首节点
            nextNode = firstNode;

            // 输出节点
            System.out.println("====创建链表赋值,输出节点=====");
            printNode(nextNode);

            /*
            补充说明: 在对节点进行替换或删除的时候，被替换或被删节点的next引用不需要设置为null,因为一个对象被回收的前提是
            因为没有任何地方持有这个对象的引用（引用计数器为0）也就是说它不在被引用，那么那么它将被回收，至于它引用什么对象
            无关紧要，因为对于它所引用的对象来说依然是看引用计数器是否为0；
             */

            // 替换节点
            System.out.println("====替换节点=====");
            while (nextNode.next != null) {
                if (nextNode.val == 3) {
                    ListNode<Integer> replaceNode = new ListNode<>(520);
                    // 保存要替换节点的下一个节点
                    ListNode<Integer> node = nextNode.next.next;
                    // 被替换节点,指向为空(垃圾回收)
                    nextNode.next.next = null;
                    // 插入新节点
                    nextNode.next = replaceNode;
                    // 新节点的下一个节点的指向,之前保存的节点
                    replaceNode.next = node;
                }
                nextNode = nextNode.next;
            }
            // 循环完成之后 nextNode指向最后一个节点,重新赋值让它指向首节点
            nextNode = firstNode;
            printNode(nextNode);

            // 删除节点
            System.out.println("====替换节点=====");
            while (nextNode.next != null) {
                if (nextNode.val == 3) {
                    // 保存要删除节点的下一个节点
                    ListNode<Integer> node = nextNode.next.next;
                    // 删除节点,置为null(垃圾回收)
                    nextNode.next.next = null;
                    nextNode.next = node;
                }
                nextNode = nextNode.next;
            }
            // 循环完成之后 nextNode指向最后一个节点,重新赋值让它指向首节点
            nextNode = firstNode;
            printNode(nextNode);
        }

        /**
         * 输出节点
         */
        private static void printNode(ListNode<Integer> nextNode) {
            while (nextNode != null) {
                System.out.println("节点:" + nextNode.val);
                nextNode = nextNode.next;
            }
        }
    }
}
