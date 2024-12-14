package com.andrew.leetcode;

import java.util.HashMap;

/*
 *
 *
 * @author andrew
 * @since 2023/2/1
 * */
public class MaxCandyType {

    public int distributeCandies(int[] candyType) {
        int count = 0;

        HashMap<Integer, Integer> typeCount = new HashMap<>();

        for (int i = 0; i < candyType.length; i++) {
            typeCount.put(
                    candyType[i] ,
                    typeCount.get(candyType[i]) != null ?
                            typeCount.get(candyType[i]) + 1 : 1
            );
        }

        count = typeCount.keySet().size();

        // 共 count种糖
        if (count >= candyType.length/2) {
            return candyType.length/2;
        } else {
            return count;
        }
    }
}
