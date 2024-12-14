package com.andrew.leetcode;

/*
 *
 *
 * @author andrew
 * @since 2023/2/19
 * */
public class MergeSortedList {

    public ListNode Merge(ListNode list1,ListNode list2) {

        ListNode startNode = new ListNode(list1.val);

        if (list2.val < startNode.val) {

        }

        return null;
    }

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
