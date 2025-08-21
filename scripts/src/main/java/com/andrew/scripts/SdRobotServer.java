package com.andrew.scripts;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 思迪机器人控制协议对接(文档见 <a href="https://docs.qq.com/pdf/DVkZKS09uQm9hSEVX">协议文档</a>)
 *
 * @author tongwenjin
 * @since 2025-8-21
 */
@Slf4j
public class SdRobotServer {

    enum ACTION {

        STOP("12", "紧急暂停"),
        RESET("13", "复位"),
        RECOVERY("14", "暂停恢复"),
        EXE("15", "执行动作"),
        EXE_FIN("16", "执行完成"),
        RESET_FIN("22", "复位完成"),
        EXE_GROUP("31", "执行动作组"),
        EXE_GROUP_FIN("32", "动作组执行完成");

        String code;

        String name;

        ACTION(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public static String getName(String code) {
            for (ACTION value : values()) {
                if (value.code.equals(code)) {
                    return value.name;
                }
            }

            return "未知";
        }
    }

    public static ConcurrentHashMap<Socket, String> clientSockets = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 7000; // 监听端口
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server 已启动，监听端口 " + port);
            new Thread(SdRobotServer::handleConsoleInput).start();
            while (true) {
                // 等待客户端连接
                Socket clientSocket = serverSocket.accept();

                log.info("客户端已连接: {}", clientSocket.getInetAddress());

                // 每个客户端单独启动一个线程处理
                new Thread(() -> handleClient(clientSocket)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConsoleInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String cmd = scanner.nextLine();
                switch (cmd) {
                    case "stop": {
                        sendAll("12", "01");
                        break;
                    }
                    case "reset": {
                        // 复位
                        sendAll("13", "01");
                        break;
                    }
                    case "stop_recovery": {
                        // 暂停恢复
                        sendAll("14", "01");
                        break;
                    }
                    case "exe": {
                        // 暂停恢复
                        sendAll("15", "0002");
                        break;
                    }
                    case "exe_group": {
                        sendAll("31", "01");
                        break;
                    }
                    case "exe_group_fin": {
                        sendAll("32", "01");
                        break;
                    }
                }
            }
        }
    }

    private static void sendAll(String action, String code) {
        for (Map.Entry<Socket, String> entry : clientSockets.entrySet()) {
            Socket socket = entry.getKey();
            String sn = entry.getValue();
            if (socket.isConnected()) {
                try {
                    OutputStream os = socket.getOutputStream();
                    // 发送暂停指令
                    String prefix = "5344";
                    String cmd = "01";
                    String dataLength = code.length() == 2 ? "000F" : "0010";
                    String seq = "00";
                    String data = cmd + dataLength + seq + action + sn + code;
                    String checkSum = calcChecksum(data);
                    String suffix = "494F54";
                    String hex = prefix + data + checkSum + suffix;
                    byte[] bytes = hexToBytes(hex);
                    os.write(bytes);
                    os.flush();
                    log.info("发送指令({} - {})至:{}成功!", action, formatHexWithSpace(hex), sn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] hexToBytes(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }
        // 去掉空格，转大写，保证统一格式
        hex = hex.replaceAll("\\s+", "").toUpperCase();
        int length = hex.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("十六进制字符串长度必须为偶数");
        }

        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    private static void handleClient(Socket clientSocket) {
        try (
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream()
        ) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
                // 这里处理接收到的二进制数据
                String hex = bytesToHex(buffer, len);
                log.info("received: {}", formatHexWithSpace(hex));

                if (!hex.startsWith("5344") || !hex.endsWith("494F54")) {
                    log.error("协议非法!");
                    return;
                }

                String cmd = hex.substring(4, 6);

                switch (cmd) {
                    // 控制
                    case "01": {
                        String action = hex.substring(12, 14);
                        log.info("action {} 执行成功!", ACTION.getName(action));
                        break;
                    }
                    // 心跳
                    case "02": {
                        String sn = hex.substring(12, 36);
                        if (!clientSockets.contains(sn)) {
                            log.info("成功建立连接:{}", sn);
                            clientSockets.putIfAbsent(clientSocket, sn);
                        }
                        out.write(buffer, 0, len);
                        break;
                    }
                    // 校时
                    case "04": {

                    }
                    // 主机状态上传
                    case "05": {
                        boolean mainPowerFault = hex.startsWith("00", 38);
                        boolean subPowerFault = hex.startsWith("00", 40);
                        boolean normalMode = hex.startsWith("00", 42);
                        boolean authed = hex.startsWith("00", 44);
                        boolean stopped = hex.startsWith("00", 46);
                        boolean doorOpened = hex.startsWith("00", 48);
                        boolean connected = hex.startsWith("00", 50);
                        boolean xNormal = hex.startsWith("00", 52);
                        boolean yNormal = hex.startsWith("00", 54);
                        boolean zNormal = hex.startsWith("00", 56);

                        log.info(
                                "机器人上报状态(主电故障:{} ,备电故障:{} ,模式:{} ,授权状态:{} ,紧急暂停:{} ,环境状态:{} ,通讯状态:{} ,X轴:{} ,Y轴:{} ,Z轴:{})",
                                mainPowerFault ? "正常" : "故障",
                                subPowerFault ? "正常" : "故障",
                                normalMode ? "正常" : "调试",
                                authed ? "已授权" : "未授权",
                                stopped ? "正常" : "暂停",
                                doorOpened ? "正常" : "主机门打开",
                                connected ? "正常" : "异常",
                                xNormal ? "正常" : "异常",
                                yNormal ? "正常" : "异常",
                                zNormal ? "正常" : "异常"
                        );

                        // 响应数据
                        String sn = hex.substring(14, 38);
                        String data = "05000F" + hex.substring(10, 12) + "01" + sn + "01";
                        String checkSum = calcChecksum(data);
                        String respHex = "5344" + data + checkSum + "494F54";
                        out.write(hexToBytes(respHex));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                clientSockets.remove(clientSocket);
                log.warn("客户端连接已关闭");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String bytesToHex(byte[] bytes, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString().trim();
    }

    public static String formatHexWithSpace(String hex) {
        if (hex == null) return "";
        hex = hex.replaceAll("\\s+", "").toUpperCase(); // 去掉已有空格并统一大写

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            if (i > 0) sb.append(" ");
            sb.append(hex, i, i + 2);
        }
        return sb.toString();
    }

    public static String calcChecksum(String hexStr) {
        if (hexStr == null || hexStr.isEmpty()) {
            return "00";
        }
        // 去掉空格
        hexStr = hexStr.replaceAll("\\s+", "").toUpperCase();

        // 必须是偶数长度
        if (hexStr.length() % 2 != 0) {
            throw new IllegalArgumentException("十六进制字符串长度必须为偶数");
        }

        int sum = 0;
        for (int i = 0; i < hexStr.length(); i += 2) {
            String byteStr = hexStr.substring(i, i + 2);
            int value = Integer.parseInt(byteStr, 16);
            sum += value;
        }

        // 取低8位
        return String.format("%02X", (byte) (sum & 0xFF));
    }
}
