package com.zhian;

import lombok.Data;

import java.util.Date;

/**
 * 消息对象,网关处理之后，放入MQ的消息体
 *
 * @author yepanpan
 * @since 2024 /5/13
 */
@Data
public class MqMessage {
    /**
     * 告警事件
     */
    public static final String EVENT_ALARM = "alarm";

    /**
     * 巡检事件
     */
    public static final String EVENT_BUSINESS = "business";

    /**
     * 设备事件
     */
    public static final String EVENT_DEVICE = "device";

    /**
     * 级联事件
     */
    public static final String EVENT_CASCADE = "cascade";

    /**
     * 设备新增事件
     */
    public static final String DEVICE_ADD = "1";

    /**
     * 设备删除事件
     */
    public static final String DEVICE_REMOVE = "2";

    /**
     * 设备修改事件
     */
    public static final String DEVICE_UPDATE = "3";

    /**
     * 消息唯一ID
     */
    private String uuid;

    /**
     * 网关对应的设备ID
     */
    private Long deviceId;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 消息类别:heart是心跳数据，alarm是告警数据,device是设备数据，cascade是级联响应
     */
    private String event;

    /**
     * 事件代码，一般就是告警代码,如果是设备消息，1是新增设备，2是删除设备
     */
    private String eventType;

    /**
     * 图片地址（http或file），多个以,分割
     * 一般作为告警图片使用
     */
    private String imageUrl;

    /**
     * 时间
     */
    private Date time;

    /**
     * The Facility.
     */
    private Facility facility;

    /**
     * 消息主体
     */
    private String msgData;


    /**
     * 告警消息
     *
     * @param deviceId  设备ID
     * @param protocol  设备所属对接协议
     * @param facility  设备信息
     * @param eventType 告警类型
     * @param msgData   告警源消息体
     * @param imageUrl  告警图片
     * @return the mq message
     */
    public static MqMessage createAlarm(
            Long deviceId, String protocol, Facility facility,
            String eventType, String msgData, String imageUrl
    ){
        MqMessage mqMessage = new MqMessage();
        mqMessage.setImageUrl(imageUrl);
        mqMessage.setDeviceId(deviceId);
        mqMessage.setEvent(MqMessage.EVENT_ALARM);
        mqMessage.setEventType(eventType);
        mqMessage.setFacility(facility);
        mqMessage.setMsgData(msgData);
        mqMessage.setTime(new Date());
        mqMessage.setProtocol(protocol);
        mqMessage.setUuid(SnowflakeIdWorker.getInstance().nextStringId());
        return mqMessage;
    }


    /**
     * The type Facility.
     */
    @Data
    public static class Facility{
        /**
         * 分类代码，青鸟是数字代码，其它平台可能传固定的设备分类代码
         */
        private String type;
        /**
         * 设备代码
         */
        private String code;

        /**
         * 通道号
         */
        private Integer chn;
        /**
         * 设备别名/安装位置
         */
        private String name;
        /**
         * 设备型号
         */
        private String model;
        /**
         * 设备传输设备（网关）
         */
        private String net;
        /**
         * 是否无线设备：1是0否
         */
        private boolean isWireless;
        /**
         * 是否在线：1是0否
         */
        private boolean isOnline;

        /**
         * The In online.
         */
        private boolean inOnline;

        /**
         * Set on line.
         *
         * @param isOnline the is online
         */
        public void setOnLine(Boolean isOnline){
            //兼容之前的拼写错误
            this.isOnline = isOnline;
            this.inOnline = isOnline;
        }

        /**
         * 信号量
         */
        private Integer rssi;
        /**
         * 电压
         */
        private Integer voltage;
        /**
         * 设备温度
         */
        private Integer temperature;

        /**
         * 平台ID
         */
        private String pfCode;
    }

}
