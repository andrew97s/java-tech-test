package com.andrew.java.tools;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 短信发送工具
 *
 * @author tongwenjin
 * @since 2024/2/26
 */
@Slf4j
public class SmsSendUtils {

    private static String filePath = "C:\\Users\\Administrator\\Desktop\\xx.txt";
    private static String textContent = "尊敬的平台供应商你好：\n" +
            "       湖北联投电子采购平台供应商参与湖北工建、湖北路桥专区项目的方式已更改，你公司还未上传或更新业绩信息，请及时登录系统点击【系统管理】-【供应商会员详情】- 【资料变更】，更新后提交审核，避免影响投标。请留意湖北联投电子采购平台网站（https://www.ltzxgl.com.cn/）发布的项目公告信息。如有问题，请拨打客服电话：4001276998或4001685996。";
    private static Integer requestLimit = 100;


    public static void main(String[] args) {
        HttpRequest post = HttpUtil.createPost("http://58.49.42.131:2100/ebidding/api/base/system/organization/register/sendMsg");

        post.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IiIsInByZWZlY3R1cmVDb2RlIjoibnVsbCIsIm9yZ05hbWUiOiLogZTmipXpm4blm6It5bmz5Y-w6L-Q6JCl6YOoIiwiaXAiOiI1OS4xNzIuMTM4LjE3MiIsImNvbXBhbnlOYW1lIjoi6IGU5oqV6ZuG5ZuiLeW5s-WPsOi_kOiQpemDqCIsImRlcHRJZCI6MCwidXNlck5hbWUiOiJhZG1pbiIsInVzZXJJZCI6IjEiLCJvcmdJZCI6MSwicmFuZG9tIjoiMTcwODk0NzczNjk0MSIsImF1ZCI6IiIsImNvbXBhbnlJZCI6MSwiZXhwIjoxNzA4OTQ5NTM2fQ.oKQCF2sRpxTXknrZ_o57o1o3NRMVFU7zXYYdcRztvWw");

        post.setRest(true);

        post.body("");

        int count = 0;

        for (String param : getParams()) {
            long startTime = System.currentTimeMillis();
            post.body(param);

            try (HttpResponse response = post.execute()) {

                System.out.println(response.body());

                log.info("第{}次调用,状态:{},耗时:{}ms", ++count, response.getStatus(), System.currentTimeMillis() - startTime);
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static List<String> getParams() {

        List<String> phones = FileUtil.readLines(new File(filePath), StandardCharsets.UTF_8);

        log.info("文件读取手机号共:{}个", phones.size());

        phones = phones.stream().distinct()
                .filter(phone -> phone.length() == 11 && StrUtil.isNumeric(phone))
                .collect(Collectors.toList());

        log.info("过滤去重后共:{}个", phones.size());

        List<List<String>> subLists = ListUtil.split(phones, requestLimit);

        return subLists.stream().map(subList -> {
            JSONObject json = new JSONObject();
            try {
                json.put("mobiles", String.join(",", subList));
                json.put("content", textContent);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return json.toString();
        }).collect(Collectors.toList());
    }
}
