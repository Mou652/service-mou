package com.mou.leetcode;

import java.util.Arrays;

/**
 * 数组实现栈
 * 自己实现一个栈，要求这个栈具有`push()`、`pop()`（返回栈顶元素并出栈）、`peek()` （返回栈顶元素不出栈）、`isEmpty()`、`size()`这些基本的方法。
 *
 * @author mou
 */
public class MyStack {
    /**
     * 存放栈中元素的数组
     */
    private int[] storage;
    /**
     * 栈的容量
     */
    private int capacity;
    /**
     * 栈中元素数量
     */
    private int count;
    private static final int GROW_FACTOR = 2;

    /**
     * 不带初始容量的构造方法。默认容量为8
     */
    public MyStack() {
        this.capacity = 8;
        this.storage = new int[8];
        this.count = 0;
    }

    /**
     * 带初始容量的构造方法
     *
     * @param initialCapacity
     */
    public MyStack(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Capacity too small.");
        }

        this.capacity = initialCapacity;
        this.storage = new int[initialCapacity];
        this.count = 0;
    }

    /**
     * 入栈
     *
     * @param value
     */
    public void push(int value) {
        if (count == capacity) {
            ensureCapacity();
        }
        storage[count++] = value;
    }

    /**
     * 确保容量大小
     */
    private void ensureCapacity() {
        int newCapacity = capacity * GROW_FACTOR;
        storage = Arrays.copyOf(storage, newCapacity);
        capacity = newCapacity;
    }

    /**
     * 返回栈顶元素并出栈
     *
     * @return
     */
    private int pop() {
        if (count == 0) {
            throw new IllegalArgumentException("Stack is empty.");
        }
        count--;
        return storage[count];
    }

    /**
     * 返回栈顶元素不出栈
     *
     * @return
     */
    private int peek() {
        if (count == 0) {
            throw new IllegalArgumentException("Stack is empty.");
        } else {
            return storage[count - 1];
        }
    }

    /**
     * 判断栈是否为空
     *
     * @return
     */
    private boolean isEmpty() {
        return count == 0;
    }

    /**
     * 返回栈中元素的个数
     */
    private int size() {
        return count;
    }
}

