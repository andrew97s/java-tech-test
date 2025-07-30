package com.andrew;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * 串口电源控制
 *
 * @author tongwenjin
 * @since 2025-4-29
 */
@Slf4j
public class SerialPower {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入内容（输入 exit 退出）：");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                log.info("程序结束。");
                break;
            }

            switch (input) {
                case "on":
                    powerOn();
                    break;
                case "off":
                    powerOff();
                    break;
                default:
                    log.error("未知命令!");
            }
        }
    }

    public static void powerOn() {
        // 给设备（串口）上电
        RuntimeUtil.exec("/bin/bash", "-c", "echo 1 > /sys/class/gpio_innohi/ext_uart_pwr/value");
        ThreadUtil.sleep(1200);
        // 启动gst推流进程
        RuntimeUtil.exec("/bin/bash", "-c", "sudo systemctl start gst");
        log.info("gst 启动成功!");
        // 等待秒后返回
        ThreadUtil.sleep(100);
        // 直接返回SRS 代理视频流
        log.info("串口上电成功");
    }

    public static void powerOff() {
        RuntimeUtil.exec("/bin/bash", "-c", "echo 0 > /sys/class/gpio_innohi/ext_uart_pwr/value");
        RuntimeUtil.exec("/bin/bash", "-c", "sudo systemctl stop gst");
        log.info("串口断电成功");
    }
}
