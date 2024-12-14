package com.andrew.leetcode;

/*
 * 扑克牌顺子 - 简单问问题
 *
 * @author andrew
 * @since 2023/1/31
 * */
public class NewcodeJZ61 {

    public static void main(String[] args) {
        System.out.println(new NewcodeJZ61().IsContinuous(new int[]{6,0,2,0,4}));
    }

    public boolean IsContinuous(int [] numbers) {
        // 先进行排序
        for (int i = 0; i < numbers.length; i++) {
            int min = numbers[i];
            int minIndex = i;

            for (int j = i+1; j < numbers.length; j++) {

                if (numbers[j] < min) {
                    min  = numbers[j];
                    minIndex = j;
                }
            }

            // 执行交换
            if (minIndex != i) {
                int number = numbers[i];
                numbers[i] = min;
                numbers[minIndex] = number;
            }
        }

        int jokerCount = 0;
        // 获取 大小王 数量
        for (int number : numbers) {
            if (number == 0) {
                jokerCount++;
            }
        }

        // 判断是否连续递增
        for (int i = jokerCount + 1; i < numbers.length; i++) {
            int offset = numbers[i] - numbers[i-1];

            if(offset ==0) {
                return false;
            } else
            if (offset != 1 && jokerCount == 0) {
                return false;
            } else if (offset -1 > jokerCount ){
                return false;
            } else {
                jokerCount-=(offset-1);
            }
        }

        return true;
    }
}
