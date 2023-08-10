package com.andrew.leetcode.easy;

import com.andrew.leetcode.meduim.Leetcode54;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 2085. 统计出现过一次的公共字符串
 *
 * @author tongwenjin
 * @since 2023/8/10
 */
public class Leetcode2085 {
    public static void main(String[] args) {
        String[] words1 = new String[]{"leetcode","is","amazing","as","is"};
        String[] words2 = new String[]{"amazing","leetcode","is"};

        System.out.println(new Leetcode2085().countWords(words1, words2));
    }

    public int countWords(String[] words1, String[] words2) {

        Map<String,Integer> map1 = new HashMap<>();

        for (String word1 : words1) {
            Integer count = map1.get(word1);
            map1.put(word1 , count != null ? count+1 : 1);
        }

        Map<String,Integer> map2 = new HashMap<>();

        for (String word2 : words2) {
            Integer count = map2.get(word2);
            map2.put(word2 , count != null ? count+1 : 1);
        }

        List<String> list1 = map1.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        List<String> list2 = map2.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        list1.retainAll(list2);
        return list1.size();
    }

}
