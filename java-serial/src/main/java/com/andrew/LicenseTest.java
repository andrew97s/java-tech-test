package com.andrew;

import java.util.Base64;

/**
 * @author tongwenjin
 * @since 2025-6-20
 */
public class LicenseTest {
    public static void main(String[] args) {
        System.out.println(
                new String(Base64.getDecoder().decode("ODE3NmFmMzM5MTRmNWNlYy0wNjY3N2VlMWI1NTA0NjkxOThkNWE2YThkNjc1OThiNQ==".getBytes()))
        );
    }
}
