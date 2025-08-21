package com.zhian;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 接入设备对象 za_sys_device
 * 
 * @author yepanpan
 * @date 2024-05-13
 */
@Data
public class ZaSysDevice
{
    private Long id;

    private String bizId;

    private String type;

    private String model;

    private String net;

    private String code;

    private String name;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private String wireless;

    private String pfCode;

    private Long cascadeId;

    private String online;

    private String ip;
}
