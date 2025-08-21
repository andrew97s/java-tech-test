package com.zhian;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 设施信息
 */
@Data
public class Facility implements Serializable {


    /**
     * 设备ID
     */
    private Long id;

    /** 设施类别     */
    private Integer facilitiesCode;
    private String facilities;


    /**  设施标识,代码（独立烟感PSN）     */
    private String addrStr;

    /**  设施注释    */
    private String descr;

    /**  * 是否为无线设施     */
    private String isWireless;

    /**  设施类型     */
    private Integer facilitiesTypeCode;

    private String facilitiesType;


    /**  模拟量类型     */
    private String analogType;
    /**  模拟量     */
    private BigDecimal analogValue;

    /**  传输设备（网关）序列号     */
    private String net;

    /**  温度   */
    private String temperature;

    /**   模拟量高阈值 */
    private BigDecimal analogThresholdHigh;
    /** 模拟量低阈值 */
    private BigDecimal analogThresholdLow;

    /**  传感器类型 */
    private Integer sensorTypeCode;
    private String sensorType;


    /**  设施原始标识     */
    private String addrSrc;


    /**  气灭区编号 气灭控制器专用 */
    private Integer part;

    /** double 压力 末端试水专用 */
    private BigDecimal pressure;
    private BigDecimal pressureThresholdHigh;
    private BigDecimal pressureThresholdLow;
    /** double 流量 末端试水专用 **/
    private BigDecimal flow;
    private BigDecimal flowThresholdLow;
    private BigDecimal flowThresholdHigh;
    /** 开启时长(s) 末端试水专用 */
    private Integer runDuration;
    /** 0-手动 1-自动 末端试水专用 */
    private Integer manualAutoMode;

    /**  设施型号 */
    private Integer facilitiesModelId;
    /** 0-电动阀闸关闭 1-电动阀开启 末端试水专用 **/
    private Integer switchs;
    /**  波动报警恢复时间 1-60分钟 */
    private Integer fluctuationRecoverTime;

    /**  波动阈值,变化波动报警 百分比 1~99%  */
    private BigDecimal fluctuationThreshold;

    private String facilitiesModel;
    private String model;

    /**
     * 通道号
     */
    private String chn;


    private BigDecimal thresholdHigh;
    private BigDecimal thresholdLow;

    /** 青鸟无线设备坐标默认使用BD09(百度地图)坐标系 */
    private BigDecimal latitude;
    private BigDecimal longitude;

    private String extraInfo;
    private String voltage;
    private String rssi;

    //V3A3
    private String currentA;
    private String currentB;
    private String currentC;
    private String directConnect;
    private String energyUsedA;
    private String energyUsedB;
    private String energyUsedC;
    private String energyUsedMainAlert;
    private String energyUsedMainInitial;
    private String voltageA;
    private String voltageB;
    private String voltageC;

    //断路器
    private String currentMain;
    private String voltageMain;

    public boolean isWireless(){
        return isWireless != null &&( isWireless.equalsIgnoreCase("1") || isWireless.equalsIgnoreCase("true"));
    }
}
