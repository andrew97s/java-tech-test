package com.andrew;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

/**
 * @author tongwenjin
 * @since 2025-5-14
 */
@Slf4j
public class FileRead {

    private static final String DEVICE_URI = "/sys/class/gpio_innohi/ext_uart_pwr/value";


    public static void main(String[] args) {

        System.out.println(new String(Base64.getDecoder().decode("LThmMWMzN2E1ZDk5NDQxM2Q4MmQ4ZTUzODgyZTY2Yzk0")));
        System.out.println(new String(Base64.getDecoder().decode("QkZFQkZCRkYwMDA5MDY3NS0yNDAyMzM4NzIzMDAwNzA=")));
//        System.out.println(args.length);
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.print("请输入内容（输入 exit 退出）：");
//            String input = scanner.nextLine();
//
//            if ("exit".equalsIgnoreCase(input)) {
//                log.info("程序结束。");
//                break;
//            }
//
//            switch (input) {
//                case "file":
//                    readFile();
//                    break;
//                case "cat":
//                    readCat();
//                    break;
//                case "testFile":
//                    int count = Integer.parseInt(scanner.nextLine());
//                    long start = System.currentTimeMillis();
//                    for (int i = 0; i < count; i++) {
//                        readFile();
//                    }
//                    log.info("cost time : {}", System.currentTimeMillis() - start);
//                    break;
//                case "testCat":
//                    int catCount = Integer.parseInt(scanner.nextLine());
//                    long catStart = System.currentTimeMillis();
//                    for (int i = 0; i < catCount; i++) {
//                        readCat();
//                    }
//                    log.info("cost time : {}", System.currentTimeMillis() - catStart);
//                    break;
//                default:
//                    log.info("未知命令:{}", input);
//            }
//        }
    }

    private static void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(DEVICE_URI))) {
            String line = br.readLine();
            System.out.println("GPIO value: " + line);
        } catch (IOException e) {
            System.err.println("读取失败: " + e.getMessage());
        }
    }

    private static void readCat() {
        System.out.println(RuntimeUtil.execForStr("/bin/bash", "-c", "sudo cat " + DEVICE_URI).trim());
    }
}
