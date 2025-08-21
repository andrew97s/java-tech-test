package com.andrew;

import com.fazecast.jSerialComm.SerialPort;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author tongwenjin
 * @since 2025-8-13
 */

@Slf4j
public class MotorTest {


    // 串口对象
    private static SerialPort serialPort;
    private static Thread readThread;
    private OutputStream outputStream;

    private CompletableFuture<String> freqFuture = new CompletableFuture<>();
    private CompletableFuture<String> addressFuture = new CompletableFuture<>();
    private CompletableFuture<String> dataFuture = new CompletableFuture<>();
    private CompletableFuture<Boolean> writeFuture = new CompletableFuture<>();

    private void connectPort(String name) {
        try {
            closePort();
            // 获取串口
            serialPort = SerialPort.getCommPort(name);
            if (!serialPort.openPort()) {
                log.error("串口打开失败!");
                return;
            }

            // 配置串口参数
            serialPort.setBaudRate(9600);
            serialPort.setNumDataBits(8);
            serialPort.setNumStopBits(1);
            serialPort.setParity(SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

            // 获取输入流和输出流
            InputStream inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();

            // 启动读取线程
            readThread = new Thread(() -> readFromSerial(inputStream), name + "-reader");
            readThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close port.
     */
    public void closePort() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
            serialPort = null;
        }
        if (readThread != null && readThread.isAlive()) {
            readThread.interrupt();
        }
    }

    // 读取串口数据
    private void readFromSerial(InputStream inputStream) {
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                int len = inputStream.read(buffer);
                if (len > 0) {
                    StringBuilder hexString = new StringBuilder();
                    for (int i = 0; i < len; i++) {
                        hexString.append(String.format("%02X ", buffer[i]));
                    }

                    String cmd = hexString.toString().replace(" ", "");
                    log.debug("接收到数据: {}", hexString);
//                    processCmd(cmd);
                } else {
                    if (Thread.currentThread().isInterrupted()) {
                        // 线程中断
                        log.warn("read线程收到中断请求,退出处理。。。");
                        break;
                    }
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            // 忽略interrupt
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCmd(String resp) {
        // 命令
        String cmd = resp.substring(0, 2);
        // 结果
        String status = resp.substring(8, 10);

        switch (cmd) {
            // 读取频率
            case "03": {
                if ("00".equals(status) && resp.length() == 24) {
                    String cardType = resp.substring(10, 14);
                    String cardCode = resp.substring(14, 22);
                    log.info("读取到卡片信息,cardType:{},cardCode:{}", cardType, cardCode);
                    freqFuture.complete(cardCode);
                } else {
                    freqFuture.complete("");
                    log.error("读取卡片code失败!");
                }
                break;
            }
            default:
                log.error("命令({})未知!", cmd);
        }
    }

    /**
     * 发送数据到串口
     *
     * @param outputStream the output stream
     * @param msg          the msg
     */
    public void sendToSerial(OutputStream outputStream, byte[] msg) {
        // 校验和
         msg = calculateCRC(msg);

        try {
            outputStream.write(msg);
            log.debug("发送数据: {}", byteArrayToHex(msg));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] calculateCRC(byte[] data) {
        int crc = 0xFFFF; // 初始值
        byte[] result = new byte[data.length + 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            result[i] = b;
            crc ^= (b & 0xFF);
            for (int n = 0; n < 8; n++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >> 1) ^ 0xA001; // 多项式 0xA001
                } else {
                    crc = crc >> 1;
                }
            }
        }

        result[result.length - 2] = (byte) (crc & 0xFF);       // 低字节
        result[result.length - 1] = (byte) ((crc >> 8) & 0xFF); // 高字节

        return result;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray the byte array
     * @return the string
     */
    public String byteArrayToHex(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02X ", b));
        }
        return hexString.toString();
    }

    /**
     * 将十六进制字符串（无空格）转为字节数组
     *
     * @param hex the hex
     * @return the byte [ ]
     */
    public byte[] hexStringToBytes(String hex) {
        hex = hex.replaceAll("\\s", ""); // 移除空格
        hex = hex.replaceAll("00", ""); // 移除空格

        int len = hex.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("十六进制字符串长度必须为偶数");
        }
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        // 剔除0字节
        List<Byte> tempList = new ArrayList<>();
        for (byte b : bytes) {
            if (b != 0) {
                tempList.add(b);
            }
        }
        byte[] result = new byte[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            result[i] = tempList.get(i);
        }
        return result;
    }

    /**
     * 读取卡片编号
     *
     * @return the string
     */
    public String getFrequency() {
        byte[] cmdBytes = {
                0x01, 0x03, 0x00, 0x22, 0x00, 0x02
        };
        sendToSerial(outputStream, cmdBytes);

        try {
            String sn = freqFuture.get(3, TimeUnit.SECONDS);
            freqFuture = new CompletableFuture<>();
            return sn;
        } catch (Exception e) {
            return null;
        }
    }

    public void openChannel() {
        byte[] cmdBytes = {
                0x01, 0x06, 0x05, (byte) 0xE1, 0x00, 0x2D
        };
        sendToSerial(outputStream, cmdBytes);
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        MotorTest main = new MotorTest();

//        main.connectPort(args[0]);
        main.connectPort("COM7");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("请输入内容（输入 exit 退出）：");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                log.info("程序结束。");
                break;
            }

            switch (input) {
                case "address":
                    log.info("读取读卡器地址...");
                    System.out.println(main.getFrequency());
                    break;
                case "channel":
                    log.info("open channel...");
                    main.openChannel();
                    break;
                default:
                    log.info("未知命令:{}", input);
            }
        }

        scanner.close();
        main.closePort();
    }
}
