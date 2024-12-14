package com.andrew.test;

import java.util.Arrays;
import java.util.Scanner;

/*
 *
 *
 * @author andrew
 * @since 2023/2/2
 * */
public class TestClass3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {

            // 初始化赋值
            int soldierCount = Integer.parseInt(sc.nextLine());
            int totalTime = Integer.parseInt(sc.nextLine());
            String timeStr = sc.nextLine();

            int[] times = new int[soldierCount];
            String[] timeStrs = timeStr.split(" ");
            for (int i = 0; i < soldierCount; i++) {
                times[i] = Integer.parseInt(timeStrs[i]);
            }

            // 排序
            Arrays.sort(times);

            int costTime = 0;
            int saveSolider = 0;

            // 1人
            if (times[0] <= totalTime && times[1] > totalTime ) {
                System.out.println("1 " + (times[0]));
            }

            // 2 人过河
            if (times[1] <= totalTime && times[0] + times[1] + times[2] > totalTime) {
                System.out.println("2 " + (times[1]));
            }
            // 3 人过河
            else
            if (
                    times[1] + times[0] + times[2] <= totalTime &&
                            times[1] + times[0] + times[2] + times[0]+ times[3] > totalTime
            ) {
                System.out.println("3 " + (times[1] + times[0] + times[2]));
            }
            // 4人过河
            else
            if (
                    times[1] + times[0] + times[2]+ times[0] + times[3]  <= totalTime &&
                            times[1] + times[0] + times[2] + times[0] + times[3] + times[0] + times[4] > totalTime

            ) {
                System.out.println("4 " + (times[1] + times[0] + times[2] + times[0] + times[3]));
            }
            else
            if (times[1] + times[0] + times[2] == totalTime) {
                System.out.println("3 " + times[1] + times[0] + times[2]);
            }
        }
    }
}
