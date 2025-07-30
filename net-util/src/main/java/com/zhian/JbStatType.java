package com.zhian;

import lombok.Getter;

/**
 * @author tongwenjin
 * @since 2025-6-25
 */
@Getter
public enum JbStatType {
    FIRE(1,"火警", "3"),
    WARNING(2,"预警", "2"),
    LINKAGE(3,"联动", "1"),
    FEEDBACK(4,"反馈", "1"),
    GUARD(5,"监管", "1"),
    FAULT(6,"故障", "2"),
    SHIELD(7,"屏蔽", "1"),
    OTHER(8,"其他", "0"),
    ;

    private int code;
    private String name;
    private String level;

    JbStatType(int code, String name, String level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public static JbStatType loadByCode(int code) {
        for (JbStatType value : values()) {
            if (value.getCode() == code) {
                return value;
            }
        }

        return OTHER;
    }
}
