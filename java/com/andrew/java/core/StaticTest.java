package com.andrew.java.core;

/**
 * @author tongwenjin
 * @since 2022/6/28
 */
public class StaticTest {

    static {
        try {
            String[] cmd = {"notepad.exe"};
            java.lang.Runtime.getRuntime().exec(cmd).waitFor();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("aa");
    }
}
