package com.zw.algorithm.common;

import java.util.ArrayList;

/**
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-23 18:48
 * @since 0.1.0
 **/
//初始化
//类名 ：Java类就是一种自定义的数据结构
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode(int x) {

        val = x;
        next = null;
    }

    //创建单链表
    public static ListNode makeNode(int[] nums) {

        if (nums.length == 0) {
            return null;
        }
        ListNode listNode = new ListNode(nums[0]);
        ListNode head = listNode;
        for (int i = 1; i < nums.length; i++) {
            ListNode node = new ListNode(nums[i]);
            listNode.next = node;
            listNode = node;
        }
        return head;
    }

    //创建循环链表
    public static ListNode makeNode(int[] nums, int pos) {

        if (nums.length == 0) {
            return null;
        }
        ListNode[] listNodes = new ListNode[nums.length];
        for (int i = 0; i < nums.length; i++) {
            listNodes[i] = new ListNode(nums[i]);
        }

        ListNode listNode = listNodes[0];
        for (int i = 1; i < listNodes.length; i++) {
            listNode.next = listNodes[i];
            listNode = listNodes[i];
        }
        if (pos >= 0 && pos < nums.length) {
            listNode.next = listNodes[pos];
        }
        return listNodes[0];
    }

    //创建两条相交链表
    public static ListNode[] makeIntersectNode(int[] listA, int skipA, int[] listB, int skipB) {

        if (listA.length == 0 || listB.length == 0) {
            return null;
        }
        ListNode[] nodesA = new ListNode[listA.length];
        for (int i = 0; i < nodesA.length; i++) {
            nodesA[i] = new ListNode(listA[i]);
        }
        ListNode nodeA = nodesA[0];
        for (int i = 1; i < nodesA.length; i++) {
            nodeA.next = nodesA[i];
            nodeA = nodesA[i];
        }

        ListNode[] nodesB = new ListNode[listB.length];
        for (int i = 0; i < nodesB.length; i++) {
            nodesB[i] = new ListNode(listB[i]);
        }
        ListNode nodeB = nodesB[0];
        for (int i = 1; i < nodesB.length; i++) {
            nodeB.next = nodesB[i];
            nodeB = nodesB[i];
        }

        if (skipA < listA.length && skipB < listB.length && listA[skipA] == listB[skipB]) {
            nodesB[skipB].next = nodesA[skipA];
        }

        ListNode[] nodes = new ListNode[2];
        nodes[0] = nodesA[0];
        nodes[1] = nodesB[0];
        return nodes;
    }

    //遍历链表
    public static ArrayList<Integer> traverse(ListNode head) {

        ArrayList<Integer> arrayList = new ArrayList<>();
        while (head != null) {
            arrayList.add(head.val);
            head = head.next;
        }
        return arrayList;
    }
}

