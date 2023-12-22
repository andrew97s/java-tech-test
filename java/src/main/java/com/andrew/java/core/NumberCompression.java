package com.andrew.java.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 数值压缩
 *
 * @author tongwenjin
 * @since 2023/11/10
 */
public class NumberCompression {

    public static void main(String[] args) {
        System.out.println(Long.toHexString(1717907499624009729L));
    }

    public static List<Integer> deltaEncode(List<Integer> numbers) {
        List<Integer> compressedNumbers = new ArrayList<>();
        compressedNumbers.add(numbers.get(0)); // 第一个数字保持不变
        for (int i = 1; i < numbers.size(); i++) {
            int delta = numbers.get(i) - numbers.get(i - 1);
            compressedNumbers.add(delta);
        }
        return compressedNumbers;
    }
}
