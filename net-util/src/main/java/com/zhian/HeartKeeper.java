package com.zhian;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author tongwenjin
 * @since 2025-7-1
 */
@Slf4j
public class HeartKeeper {

    private static List<DeviceInfo> deviceInfos = new ArrayList<>();

    private static ConcurrentHashSet<String> fireAddressCodes = new ConcurrentHashSet<>();
    private static ConcurrentHashSet<String> offlineAddressCodes = new ConcurrentHashSet<>();

    public static boolean isNetOffline = false;

    private static final String STATUS_DATA_FILE = "/home/ubuntu/http_server/status.json";
    private static final String DEVICE_FILE = "/home/ubuntu/http_server/all_device.xlsx";


    private static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public static class AddressStatus {
        public Set<String> fireAddressCodes;
        public Set<String> offlineAddressCodes;
        public boolean isNetOffline;

        public AddressStatus(Set<String> fire, Set<String> offline, boolean offlineFlag) {
            this.fireAddressCodes = fire;
            this.offlineAddressCodes = offline;
            this.isNetOffline = offlineFlag;
        }
    }

    public static void start() {
        // 加载文件状态缓存
        loadFileData();
        // 每 3 分钟执行一次心跳
        scheduler.scheduleAtFixedRate(()->{
            try {
                doHeartKeep();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.MINUTES);
        // 10s 执行一次状态保存
        scheduler.scheduleAtFixedRate(HeartKeeper::saveFileData, 10, 10, TimeUnit.SECONDS);
        // 5分钟执行一次刷新设备全量数据
        scheduler.scheduleAtFixedRate(HeartKeeper::reloadFullDevice, 15, 5, TimeUnit.MINUTES);
    }


    public static void resetFire(String addressCode) {
        fireAddressCodes.remove(addressCode);
    }

    public static void resetByController(String controllerCode) {
        fireAddressCodes.removeIf(code -> code.startsWith(controllerCode + "-"));
    }

    public static void setFire(String addressCode) {
        fireAddressCodes.add(addressCode);
    }

    public static void setOffline(String addressCode) {
        offlineAddressCodes.add(addressCode);
    }

    public static void removeOffline(String addressCode) {
        offlineAddressCodes.remove(addressCode);
    }

    private static void loadFileData() {
        String statusJson = FileUtil.readString(STATUS_DATA_FILE, StandardCharsets.UTF_8);
        AddressStatus status = JSON.parseObject(statusJson, AddressStatus.class);

        if (status != null) {
            HeartKeeper.fireAddressCodes = CollUtil.isNotEmpty(status.fireAddressCodes) ?
                    new ConcurrentHashSet<>(status.fireAddressCodes) : new ConcurrentHashSet<>();
            HeartKeeper.offlineAddressCodes = CollUtil.isNotEmpty(status.offlineAddressCodes) ?
                    new ConcurrentHashSet<>(status.offlineAddressCodes) : new ConcurrentHashSet<>();
            HeartKeeper.isNetOffline = status.isNetOffline;
        }

        log.info("本地状态缓存已加载: {}" , statusJson);
    }

    private static void reloadFullDevice() {
        ExcelReader reader = ExcelUtil.getReader(DEVICE_FILE);

        // 添加中文表头到英文字段的映射
        reader.addHeaderAlias("设备名称", "deviceName");
        reader.addHeaderAlias("测点名称", "probeName");
        reader.addHeaderAlias("相位", "phase");
        reader.addHeaderAlias("设备类型", "deviceType");
        reader.addHeaderAlias("终端编码", "terminalCode");
        reader.addHeaderAlias("地址码", "addressCode");
        reader.addHeaderAlias("生产厂家", "manufacturer");
        reader.addHeaderAlias("照片图片", "photo");
        reader.addHeaderAlias("所属通道", "channel");
        reader.addHeaderAlias("所属电缆", "cable");
        reader.addHeaderAlias("安装位置", "installLocation");
        reader.addHeaderAlias("安装完成日期", "installDate");
        reader.addHeaderAlias("所属供电分公司", "powerCompany");
        reader.addHeaderAlias("边缘设备编码", "edgeDeviceCode");
        reader.addHeaderAlias("NVR账号", "nvrAccount");
        reader.addHeaderAlias("NVR密码", "nvrPassword");
        reader.addHeaderAlias("IP地址", "ipAddress");

        // 设置使用别名
        reader.setHeaderAlias(reader.getHeaderAlias());

        // 读取为 Java Bean 列表
        deviceInfos = reader.readAll(DeviceInfo.class);
        log.debug("成功从本地文件中加载全量设备数据共{}条!" , deviceInfos.size());
    }

    private static void saveFileData() {
        AddressStatus status = new AddressStatus(
                new HashSet<>(fireAddressCodes),
                new HashSet<>(offlineAddressCodes),
                isNetOffline
        );
        try (FileWriter writer = new FileWriter(STATUS_DATA_FILE)) {
            String jsonStr = JSON.toJSONString(status); // true 表示格式化输出
            writer.write(jsonStr);
            log.debug("状态保存成功：{}", STATUS_DATA_FILE);
        } catch (IOException e) {
            log.debug("保存状态失败：{}", e.getMessage());
        }
    }

    private static void doHeartKeep() {
        log.info("try do heart ...");
        if (isNetOffline) {
            log.warn("网关已离线,不执行设备心跳逻辑!");
            return;
        }

        List<DeviceInfo> devices = getDeviceInfos();

        List<List<DeviceInfo>> subLists = ListUtil.split(devices, 50);

        for (List<DeviceInfo> subList : subLists) {
            List<PushVO> pushVOS = subList.stream()
                    .filter(device -> !offlineAddressCodes.contains(device.getAddressCode()))
                    .map(device -> {
                        PushVO pushVO = new PushVO();
                        Date date = new Date();
                        // 时间
                        pushVO.setTimestamp(DateUtil.formatDateTime(date));
                        // 检测值取事件类型
                        pushVO.setCdz(fireAddressCodes.contains(device.getAddressCode()) ? "1" : "0");
                        // 设备地址(网关 + 设备一次码)
                        pushVO.setCddzm(device.getAddressCode());
                        // 事件描述
                        pushVO.setDescription(
                                device.getDeviceName() + "(安装位置:" + device.getInstallLocation() + ") 心跳数据 "
                        );
                        // 告警级别  0：正常；1：注意；2：异常；3：严重
                        pushVO.setGjjb("-1");

                        pushVO.setZdbh("");
                        pushVO.setZzbh("");
                        pushVO.setTp("");

                        return pushVO;
                    }).collect(Collectors.toList());
            try {
                String msg = JSON.toJSONString(pushVOS);
                log.debug("push MQtt heart,count {}",pushVOS.size());
                if (CollUtil.isNotEmpty(pushVOS)) {
                     NetServer.pushMqtt(msg);
                }
                ThreadUtil.sleep(1000);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.debug("finished heart ...");
    }

    private static List<DeviceInfo> getDeviceInfos() {
        if (CollUtil.isNotEmpty(deviceInfos)) {
            return deviceInfos;
        }

        reloadFullDevice();

        return deviceInfos;
    }
}
