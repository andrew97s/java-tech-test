package com.andrew.java.collection;

import java.util.ArrayList;

/**
 * to test array lis features
 *
 * @author tongwenjin
 * @since 2023/8/3
 */
public class ArrayListTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 1; i <= 19; i++) {
            list.add("str"+i);
        }


        list.clear();

        System.out.println(list);
    }
}
