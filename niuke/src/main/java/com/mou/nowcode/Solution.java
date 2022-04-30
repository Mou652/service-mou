package com.mou.nowcode;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点并返回链表的头指针
 * 例如，
 * 给出的链表为: 1 -> 2 -> 3 -> 4 -> 5 n= 2
 * 删除了链表的倒数第 n 个节点之后,链表变为 1 → 2 → 3 → 5
 *
 * @author: mou
 * @date: 2021/4/3
 */
public class Solution {

    public static void main(String[] args) {
        ListNode<Integer> listNode = new ListNode<>(0);
        ListNode<Integer> nextNode;
        nextNode = listNode;
        for (int i = 1; i <= 5; i++) {
            nextNode.next = new ListNode<>(i);
            nextNode = nextNode.next;
        }
        nextNode = listNode;

        ListNode resultNode = removeNthFromEnd(nextNode, 3);

        while (resultNode != null) {
            System.out.println(resultNode.val);
            resultNode = resultNode.next;
        }
    }

    /**
     * 正解
     * <p>
     * 首先，我们要清楚链表的长度length，这样的话，要删除倒数第n个节点的话，就是删除正数的第（length - n）个节点（从 0 开始遍历链表）（
     * 当删除倒数第length的节点时例外，因为我们遍历链表的时候从 0 开始，所以这个时候我们需要单独将条件提出）。
     * <p>
     * 其次，当我们遍历到了要删除的节点的前一位的时候，我们只需要让其的 next 指向其 next.next，这样的话就可以删除该节点。
     * <p>
     * 最后，当我们遍历链表的时候，需要有一个指针指向该链表的首部，这样就不会丢失链表遍历位置之前的元素。
     */
    public static ListNode removeNthFromEnd(ListNode<Integer> head, int n) {
        if (head == null || n <= 0) {
            return head;
        }

        ListNode node = head;
        ListNode resultNode = head;
        ListNode beginNode = resultNode;
        int size = 0;

        // 获取链表长度
        while (node != null) {
            size++;
            node = node.next;
        }
        size = size - n;

        if (size == 0) {
            resultNode = resultNode.next;
            return resultNode;
        }

        // 获的倒数第n个节点
        for (int i = 0; i < size - 1; i++) {
            resultNode = resultNode.next;
        }
        resultNode.next = resultNode.next.next;

        return beginNode;
    }

    /**
     * 思路错误
     *
     * @param args
     */
    // public static void main(String[] args) {
    //     // 创建链表
    //     ListNode firstNode = new ListNode(0);
    //     ListNode nextNode;
    //     nextNode = firstNode;
    //     for (int i = 1; i <= 5; i++) {
    //         nextNode.next = new ListNode(i);
    //         nextNode = nextNode.next;
    //     }
    //     nextNode = firstNode;
    //     System.out.println("创建的节点");
    //     while (nextNode != null) {
    //         System.out.println("节点:" + nextNode.val);
    //         nextNode = nextNode.next;
    //     }
    //     nextNode = firstNode;
    //
    //     ListNode node = removeNthFromEnd(nextNode, 2);
    //
    //     System.out.println("删除后的节点");
    //     while (node != null) {
    //         System.out.println("节点:" + node.val);
    //         node = node.next;
    //     }
    // }
    //
    // @SuppressWarnings("all")
    // public static ListNode removeNthFromEnd(ListNode head, int n) {
    //     if (n < 1 || head == null) {
    //         return head;
    //     }
    //
    //     // 先遍历,按顺序放入list容器,用于遍历找到倒数第N个节点
    //     List nodeList = new ArrayList<>();
    //     while (head != null) {
    //         nodeList.add(head.val);
    //         head = head.next;
    //     }
    //     // 找到倒数第N个节点
    //     int reciprocal = (int) nodeList.get(n);
    //     if (reciprocal > nodeList.size()) {
    //         return head;
    //     }
    //
    //     // 找到首节点,重新赋值
    //     // head = nodeList.get(nodeList.size()-1);
    //
    //     // 删除倒数第n个节点
    //     while (head.next != null) {
    //         if (reciprocal == (int) head.val) {
    //             // 保存删除节点的下一个节点
    //             ListNode node = head.next.next;
    //             head.next.next = null;
    //             head.next = node;
    //         }
    //         head = head.next;
    //     }
    //
    //     return head;
    // }
}
