package com.zw.algorithm.introduction;


import com.zw.algorithm.common.ListNode;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 简单： 反串链表
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-06-06 11:14
 * @since 0.1.0
 **/
public class Demo001 {

    public static void main(String[] args) {

        LinkedList<String> strings = new LinkedList<>();
    }

    /**
     * 反转链表 :
     * 使用栈解决
     *
     * @param head
     * @return
     */
    public ListNode ReverseList001(ListNode head) {

        Stack<ListNode> stack = new Stack<>();
        //把链表节点全部摘掉放到栈中
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        if (stack.isEmpty()) {
            return null;
        }
        ListNode node = stack.pop();
        ListNode dummy = node;
        //栈中的结点全部出栈，然后重新连成一个新的链表
        while (!stack.isEmpty()) {
            ListNode tempNode = stack.pop();
            node.next = tempNode;
            node = node.next;
        }
        //最后一个结点就是反转前的头结点，一定要让他的next
        //等于空，否则会构成环
        node.next = null;
        return dummy;
    }

    /**
     * 反转链表 :
     * 双链表求解是把原链表的结点一个个摘掉，每次摘掉的链表都让他成为新的链表的头结点，然后更新新链表
     *
     * @param head
     * @return
     */
    public ListNode ReverseList002(ListNode head) {

        ListNode nodeNew = null;
        while (head != null) {
            // 记录一下值
            ListNode temp = head.next;
            head.next = nodeNew;
            nodeNew = head;
            head = temp;
        }
        return nodeNew;
    }

    /**
     * 递归解决
     *
     * @param head
     * @return
     */
    public static ListNode ReverseList003(ListNode head) {
        //终止条件
        if (head == null || head.next == null) {
            return head;
        }
        //保存当前节点的下一个结点
        ListNode next = head.next;
        //从当前节点的下一个结点开始递归调用
        ListNode reverse = ReverseList003(next);
        //reverse是反转之后的链表，因为函数reverseList
        // 表示的是对链表的反转，所以反转完之后next肯定
        // 是链表reverse的尾结点，然后我们再把当前节点
        //head挂到next节点的后面就完成了链表的反转。
        next.next = head;
        //这里head相当于变成了尾结点，尾结点都是为空的，
        //否则会构成环
        head.next = null;
        return reverse;
    }


    /**
     * 递归
     * @param head
     * @return
     */
    public ListNode ReverseList004(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode reverse = ReverseList004(head.next);
        head.next.next = head;
        head.next = null;
        return reverse;
    }

    public ListNode ReverseList005(ListNode head) {
        return reverseListInt005(head, null);
    }

    private ListNode reverseListInt005(ListNode head, ListNode newHead) {
        if (head == null)
            return newHead;
        ListNode next = head.next;
        head.next = newHead;
        return reverseListInt005(next, head);
    }

    public ListNode ReverseList(ListNode head) {
        return reverseListInt(head, null);
    }

    private ListNode reverseListInt(ListNode head, ListNode newHead) {
        if (head == null)
            return newHead;
        ListNode next = head.next;
        head.next = newHead;
        ListNode node = reverseListInt(next, head);
        return node;
    }

    /**
     * 练习
     * 利用链表特性
     *
     * @param head
     * @return
     */
    public ListNode ReverseList1(ListNode head) {
        /**
         *
         */
        // 第一个判断递归的结束条件
        if(head == null|| head.next == null){
            return head;
        }
        // 存储下一个节点的值
        ListNode next = head.next;
        ListNode listNode = ReverseList1(next);
        next.next = head;
        head = null;
        return listNode;
    }
}
