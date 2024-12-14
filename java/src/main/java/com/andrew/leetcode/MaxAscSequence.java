package com.andrew.leetcode;

import java.util.Arrays;

/*
 * 最长递增子序列
 *
 * 核心思想（动态规划类型）
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class MaxAscSequence {

    public static void main(String[] args) {
        System.out.println(new MaxAscSequence().lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
//        System.out.println(new MaxAscSequence().lengthOfLIS(new int[]{2,2,3}));
//        System.out.println(new MaxAscSequence().lengthOfLIS(new int[]{-2, -1}));
    }

    public int lengthOfLIS(int[] nums) {

        int[] dp = new int[nums.length];
        dp[0] = 1;

        int maxLength = 1;

        for (int i = 1; i < nums.length; i++) {

            dp[i] = 1;

            // 重新计算0 - i-1内的递增值
            for (int j = 0; j < i; j++) {

                // 新增节点 大于 内部节点
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i] , dp[j] + 1);
                }
            }


            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
    }
}
