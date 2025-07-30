package com.andrew;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tongwenjin
 * @since 2025-3-27
 */
public class Test {
    public static void main(String[] args) {

        String hex = "74 65 73 74 5F 6D 65 73 73 61 67 65";
        System.out.println(new String(new CardReader().hexStringToBytes(hex),StandardCharsets.UTF_8));
    }

    private static void tt(String hexString) {
        String resp = hexString.toString().replace(" " ,"");
        // 新的响应
        if (resp.startsWith("EFAA")) {
            String cmd = resp.substring(4,6);
            int dataLength = Integer.parseInt(resp.substring(6,10), 16) ;

            switch (cmd) {
                // REPLY
                case "00" : {
                    String msgId = resp.substring(10 , 12);
                    int result = Integer.parseInt(resp.substring(12 , 14));

                    // 成功
                    if (result == StatusEnum.SUCCESS.status) {
                        System.out.println("执行: " + msgId + " 成功 !");

                        // get_all_users
                        if (msgId.equals("24")) {
                            int totalUserCount = Integer.parseInt(resp.substring(14,16), 16);
                            List<String> userIds = new ArrayList<>(totalUserCount + 1);
                            for (int i = 0; i < totalUserCount; i++) {
                                int userId = Integer.parseInt(resp.substring(16 + i*4 , 20 + i*4));
                                userIds.add(userId + "");
                            }

                            System.out.println("查询得到共 " + totalUserCount + " 个用户");
                            for (String userId : userIds) {
                                System.out.println(userId);
                            }
                        }
                    }
                    else {
                        StatusEnum statusEnum = StatusEnum.getByCode(result);
                        String msg = statusEnum.getMsg();

                        System.out.println("执行: " + msgId + " 异常,msg: " + msg);
                    }

                    break;
                }
                // NOTE
                case "01" : {
                    break;
                }
                // IMAGE
                case "02" : {
                    break;
                }
            }
        }
        // 上一条响应的剩余部分
        else {

        }

        System.out.println("接收到数据: " + hexString.toString());
    }
}
