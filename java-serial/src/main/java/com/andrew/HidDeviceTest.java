package com.andrew;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.hid4java.HidDevice;
import org.hid4java.HidManager;
import org.hid4java.HidServices;

import java.util.List;
import java.util.Scanner;

/**
 * @author tongwenjin
 * @since 2025-5-8
 */
@Slf4j
public class HidDeviceTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入内容（输入 exit 退出）：");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                log.info("程序结束。");
                break;
            }

            switch (input) {
                case "click":
                    String data = scanner.nextLine();
                    ctrlDevice("2017", (byte) Integer.parseInt(data));
                    break;
                case "all" :
                    findHidDevice();
                    break;
                case "loop": {
                    while (true) {
                        for (int i = 0; i < 1; i++) {
                            log.info("click : {}" , i);
                            ctrlDevice("2017", (byte) i);
                            ThreadUtil.sleep(3000);
                        }
                    }
                }
                default:
                    log.error("未知命令!");
            }
        }
    }

    public static void findHidDevice(){
        // 初始化 HID 服务
        HidServices hidServices = HidManager.getHidServices();

        // 枚举所有连接的 HID 设备
        List<HidDevice> attachedHidDevices = hidServices.getAttachedHidDevices();
        for (HidDevice deviceInfo : attachedHidDevices) {
            System.out.println("发现设备: " + deviceInfo);
            System.out.println("  Vendor ID: " + Integer.toHexString(deviceInfo.getVendorId()));
            System.out.println("  Product ID: " + Integer.toHexString(deviceInfo.getProductId()));
            System.out.println("  Manufacturer: " + deviceInfo.getManufacturer());
            System.out.println("  Product: " + deviceInfo.getProduct());
            System.out.println("  Serial Number: " + deviceInfo.getSerialNumber());
        }
        System.out.println("设备数量: " + attachedHidDevices.size());
        // 关闭 HID 服务
        hidServices.shutdown();
    }


    public static HidDevice findHidDevice(String productId, String serialNumber) {
        // 初始化 HID 服务
        HidServices hidServices = HidManager.getHidServices();

        // 枚举所有连接的 HID 设备
        List<HidDevice> attachedHidDevices = hidServices.getAttachedHidDevices();
        for (HidDevice deviceInfo : attachedHidDevices) {
            if (StrUtil.isNotBlank(productId) && productId.equals(Integer.toHexString(deviceInfo.getProductId()))) {
                return deviceInfo;
            }

            if (StrUtil.isNotBlank(serialNumber) && serialNumber.equals(deviceInfo.getSerialNumber())) {
                return deviceInfo;
            }
        }
        // 关闭 HID 服务
        hidServices.shutdown();

        return null;
    }




    public static void ctrlDevice(String name, byte chn) {
        // 创建HID服务
        HidDevice device = findHidDevice(name, null);
        Assert.notNull(device, "操作失败,设备离线!");
        if (device.open()) {
            // 向设备发送数据
            byte[] data = new byte[64]; // HID报告通常是64字节
            data[0] = 0x03; // 报告ID
            data[1] = chn; // 数据字节
            data[2] = 0x01; // 数据字节
            device.write(data, data.length, (byte) 0x00);

            // 点击器按下时间
            ThreadUtil.sleep(400);

            // 向设备发送数据
            data[0] = 0x03; // 报告ID
            data[1] = chn; // 数据字节
            data[2] = 0x00; // 数据字节
            device.write(data, data.length, (byte) 0x00);

            // 关闭设备
            device.close();
        }
    }
}
