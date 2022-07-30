package com.andrew.spring;

import com.aa.TestEnableImport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tongwenjin
 * @since 2022/7/11
 */

@SpringBootApplication
@TestEnableImport
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class , args);
    }
}
