package com.andrew.leetcode;

import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 *
 *
 * @author andrew
 * @since 2023/1/31
 * */
public class Test {

    public static void main(String[] args) {
        // 97 - 122
        // 65 - 90
        // 48 - 57

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String str = in.nextLine();

            StringBuilder sb = new StringBuilder(str.substring(2));
            // 反转
            sb.reverse();

            int value = 0;

            for (int i = 0; i < sb.length(); i++) {
                char ch = sb.charAt(i);

                int chValue = 0;
                // 数字
                if (ch > 47 && ch < 58) {
                    chValue = Integer.valueOf(ch + "");
                }
                // 大写
                else if(ch > 64 && ch < 91) {
                    chValue = ch - 55;
                }
                // 小写
                else if(ch > 96 && ch < 123) {
                    chValue = ch - 87;
                }

                int scale = 1;
                for (int j = 0; j < i; j++) {
                    scale *= 16;
                }

                value += scale*chValue;
            }

            System.out.println(value);
        }
    }


}
