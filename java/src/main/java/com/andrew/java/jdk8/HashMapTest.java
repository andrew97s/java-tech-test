package com.andrew.java.jdk8;

import java.util.HashMap;

/**
 * @author tongwenjin
 * @since 2023/7/20
 */
public class HashMapTest {

    public static void main(String[] args) {


        HashMap map = new HashMap();

        for (int i = 0; i < 10; i++) {
            map.put(new Testkey() , "a"+i);
        }

        System.out.println("aa");
    }

    public static class Testkey {
        private String name;

        @Override
        public int hashCode(){
            return 1;
        }
    }
}
