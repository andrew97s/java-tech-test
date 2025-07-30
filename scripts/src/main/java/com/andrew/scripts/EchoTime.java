package com.andrew.scripts;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author tongwenjin
 * @since 2024-12-16
 */
public class EchoTime {
    public static void main(String[] args) {
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        System.out.println("JVM Default Timezone: " + TimeZone.getDefault().getID());
        System.out.println(new Date());
        System.out.println(new Date().toLocaleString());
    }
}
