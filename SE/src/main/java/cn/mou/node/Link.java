package cn.mou.node;

/**
 * 自定义链表实现
 * 单向链表的数据结构,由结点构成,每个结点只能知道下一个结点的存储位置
 *
 * @author: mou
 * @date: 2019-08-10
 */
public class Link {

    /**
     * 头结点
     */
    private Node head = null;

    class Node {
        //结点的引用,指向下一个结点
        Node next = null;
        //结点的对象
        int data;

        Node(int data) {
            this.data = data;
        }
    }

    public void addNode(int d) {
        //实例化一个结点
        Node newNode = new Node(d);
        if (head == null) {
            head = newNode;
        }
        Node temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newNode;
    }
}
