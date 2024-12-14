package com.andrew.leetcode;

import java.util.Arrays;
import java.util.Stack;

/*
 * 滑动窗口
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class SlideWindow {

    public static void main(String[] args) {
        MovingAverage move = new MovingAverage(3);
        System.out.println(move.next(1));
        System.out.println(move.next(2));
        System.out.println(move.next(3));
        System.out.println(move.next(4));
    }
}

class MovingAverage {

    public int[] ints;

    public int index = 0;

    public int size;

    /**
     * Initialize your data structure here.
     */
    public MovingAverage(int size) {
        ints = new int[size];
        this.size = size;
    }

    public double next(int val) {
        // 满了
        if (index == size) {
            int[] copyRange = Arrays.copyOfRange(ints, 1, size );
            ints = new int[size];
            for (int i = 0; i < copyRange.length; i++) {
                ints[i] = copyRange[i];
            }
            ints[size - 1] = val;
        } else {
            ints[index++] = val;
        }

        int sum = 0;

        for (int value : ints) {
            sum += value;
        }

        return (double) sum / (index);
    }
}
