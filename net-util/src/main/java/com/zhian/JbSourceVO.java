package com.zhian;

import lombok.Data;
import java.util.List;

/**
 * 青鸟告警VO
 *
 * @author tongwenjin
 * @since 2025-6-25
 */
@Data
public class JbSourceVO {

    private String event;

    private Facility facility;

    private List<Stat> stat;

    private Treat treat;


    @Data
    public static class Facility {
        private String addrSrc;
        private String addrStr;
        private String descr;
        private String facilities;
        private String facilitiesCode;
        private String facilitiesType;
        private String facilitiesTypeCode;
        private String isWireless;
        private String net;
    }

    @Data
    public static class Stat {
        private int isTreated;
        private long time;
        private int treatType;
        private int type;
        private String typeStr;
        private int val;
        private String valStr;
    }

    @Data
    public static class Treat {
        private int treatType;
        private String treatTypeStr;
    }
}
