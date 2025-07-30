package com.andrew;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tongwenjin
 * @since 2025-4-18
 */

@Data
public class TestBean extends ParentBean {

    private String name;
    private String code;
    private int count;
    private BigDecimal decimal;
    private String desc;

    public TestBean(String name, String code, int count, BigDecimal decimal, String desc) {
        this.name = name;
        this.code = code;
        this.count = count;
        this.decimal = decimal;
        this.desc = desc;
    }
}
