package com.zw.algorithm.simple;

import com.zw.algorithm.common.ListNode;

import java.util.HashSet;

/**
 * NC4 判断链表中是否有环
 *  判断给定的链表中是否有环。如果有环则返回true，否则返回false。
 *
 *
 * 数据范围：链表长度 0 \le n \le 100000≤n≤10000，链表中任意节点的值满足 |val| <= 100000∣val∣<=100000
 * 要求：空间复杂度 O(1)O(1)，时间复杂度 O(n)O(n)
 *
 * 输入分为两部分，第一部分为链表，第二部分代表是否有环，然后将组成的head头结点传入到函数里面。-1代表无环，其它的数字代表有环，这些参数解释仅仅是为了方便读者自测调试。实际在编程时读入的是链表的头节点。
 *
 * 例如输入{3,2,0,-4},1时，对应的链表结构如下图所示：
 *
 * 可以看出环的入口结点为从头结点开始的第1个结点（注：头结点为第0个结点），所以输出true。
 * @author zhouwei2
 * @version 1.0.0
 * @create 2022-08-25 14:56
 * @since 0.1.0
 **/
public class Demo004 {

    public static void main(String[] args) {

    }

    /**
     * 知识点：双指针
     * <p>
     * 双指针指的是在遍历对象的过程中，不是普通的使用单个指针进行访问，而是使用两个指针（特殊情况甚至可以多个），
     * 两个指针或是同方向访问两个链表、或是同方向访问一个链表（快慢指针）、或是相反方向扫描（对撞指针），从而达到我们需要的目的。
     * <p>
     * 思路：
     * <p>
     * 我们都知道链表不像二叉树，每个节点只有一个val值和一个next指针，也就是说一个节点只能有一个指针指向下一个节点，
     * 不能有两个指针，那这时我们就可以说一个性质：环形链表的环一定在末尾，末尾没有NULL了。为什么这样说呢？仔细看上图，
     * 在环2，0，-4中，没有任何一个节点可以指针指出环，它们只能在环内不断循环，因此环后面不可能还有一条尾巴。
     * 如果是普通线形链表末尾一定有NULL，那我们可以根据链表中是否有NULL判断是不是有环。
     * <p>
     * 但是，环形链表遍历过程中会不断循环，线形链表遍历到NULL结束了，但是环形链表何时能结束呢？
     * 我们可以用双指针技巧，同向访问的双指针，速度是快慢的，只要有环，二者就会在环内不断循环，且因为有速度差异，二者一定会相遇。
     * <p>
     * 具体做法：
     * <p>
     * step 1：设置快慢两个指针，初始都指向链表头。
     * step 2：遍历链表，快指针每次走两步，慢指针每次走一步。
     * step 3：如果快指针到了链表末尾，说明没有环，因为它每次走两步，所以要验证连续两步是否为NULL。
     * step 4：如果链表有环，那快慢双指针会在环内循环，因为快指针每次走两步，因此快指针会在环内追到慢指针，二者相遇就代表有环。
     * 图示：
     *
     * @param head
     * @return
     */
    public boolean hasCycle001(ListNode head) {
        // 先判空
        if (head == null) {
            return false;
        }
        // 初始化快慢指针
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && slow != null) {
            // 快指针
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 使用hashSet去重
     *
     * @param head
     * @return
     */
    public boolean hasCycle002(ListNode head) {

        HashSet<ListNode> listNodes = new HashSet<>();
        while (head != null) {
            if (listNodes.contains(head)) {
                return true;
            } else {
                listNodes.add(head);
            }
            head = head.next;
        }
        return false;
    }
}
