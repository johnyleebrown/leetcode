package Medium.LinkedList;

import Helpers.ListNode;

/**
 * 445
 *
 * You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * Follow up:
 * What if you cannot modify the input lists? In other words, reversing the lists is not allowed.
 */
public class AddTwoNumbersII {
    /**
     * Solution 1: modifying
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    class Solution1 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return reverse(addLists(reverse(l1), reverse(l2)));
        }

        ListNode reverse(ListNode l) {
            ListNode prev = null;
            ListNode cur = l;
            ListNode next = null;
            while (cur != null) {
                next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            return prev;
        }
    }

    /**
     * Solution 2: w/o modifying
     * Changed : creation of new ListNode
     * Beats 99,89%
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    class Solution2 {
        public  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            return reverse(addLists(reverse(l1), reverse(l2)));
        }

        ListNode reverse(ListNode l){
            ListNode prev = null;
            ListNode cur = new ListNode(l.val);
            ListNode pointer = l;
            ListNode next = null;
            while (cur != null) {
                if (pointer.next != null) {
                    cur.next = new ListNode(pointer.next.val);
                    pointer = pointer.next;
                }
                next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            return prev;
        }
    }

    ListNode addLists(ListNode l1, ListNode l2){
        ListNode result = new ListNode(0);
        ListNode head = result;
        int carry = 0;
        ListNode i = l1, j = l2;
        while (i != null || j != null) {
            int i1 = (i != null) ? i.val : 0;
            int j1 = (j != null) ? j.val : 0;
            int res = carry + i1 + j1;
            result.next = new ListNode(res % 10);
            result = result.next;
            carry = res/10;
            if (i != null) i = i.next;
            if (j != null) j = j.next;
        }
        if (carry > 0) result.next = new ListNode(carry);
        return head.next;
    }
}
