package com.zhian;

import lombok.Data;

/**
 * 推送数据VO
 *
 * @author tongwenjin
 * @since 2025-6-25
 */

@Data
public class PushVO {

    // 时间戳
    private String timestamp;

    // 监测值
    private String cdz;

    // 监测设备地址
    private String cddzm;

    // 子站编号
    private String zdbh;

    // 终端编号
    private String zzbh;

    // 告警级别  0：正常；1：注意；2：异常；3：严重
    private String gjjb = "-1";

    // 描述
    private String description;

    // 图片
    private String tp;
}
