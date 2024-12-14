package com.andrew.leetcode;

import org.omg.CORBA.NO_IMPLEMENT;

import java.util.ArrayList;
import java.util.List;

/*
 * 反转链表
 *
 * @author andrew
 * @since 2023/2/18
 * */
public class ReverseList {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        new ReverseList().reverseKGroup(node1, 1);
    }

    /**
     * @param head ListNode类
     * @param m    int整型
     * @param n    int整型
     * @return ListNode类
     */
    public ListNode reverseKGroup (ListNode head, int k) {

        if (head == null) {
            return head;
        }

        ListNode node = head;

        List<ListNode> nodes = new ArrayList<>();

        while (head.next != null) {
            nodes.add(head);
            head = head.next;
        }

        nodes.add(head);

        List<ListNode> subList = new ArrayList<>();

        ListNode startNode = null;

        for (int i = 0; i < nodes.size(); i++) {

            subList.add(nodes.get(i));
            if (subList.size() % k == 0) {


                for (int j = subList.size() - 1; j > 0; j--) {
                    subList.get(j).next = subList.get(j-1);
                }

                // 上一组翻转 末尾元素 指向当前组第一位元素
                if (startNode != null) {
                    startNode.next = subList.get(subList.size() -1);
                }

                subList.get(0).next = null;

                startNode = subList.get(0);

                node = subList.get(0) == node ? subList.get(subList.size() - 1) : node;


                subList.clear();
            }
        }

        if (subList.size() > 0 && startNode != null) {
            startNode.next = subList.get(0);
        }

        return node;
    }

    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
