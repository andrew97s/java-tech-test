package com.andrew.java.tools;

import cn.hutool.core.collection.CollUtil;
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


@Slf4j
class TestHuToolsTest {

    private static String filePath = "C:\\Users\\Administrator\\Desktop\\tek.txt";
    private static String textContent = "各位评标专家：\n" +
            "按照湖北联投电子采购平台评标（评审）专家专业的管理要求，本平台的评标（评审）专家最多可以申报两个一级专业及8个子专业，您目前的专业选择不符合上述要求，请登录系统后通过【系统管理】-【专家信息变更】变更资料，否则将于2月暂停您的专家资格。";
    private static Integer requestLimit = 100;


    @Test
    public void test() {
        HttpRequest post = HttpUtil.createPost("http://58.49.42.131:2100/ebidding/api/base/system/organization/register/sendMsg");

        post.header("token", "eyJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IiIsInByZWZlY3R1cmVDb2RlIjoibnVsbCIsIm9yZ05hbWUiOiLogZTmipXpm4blm6It5bmz5Y-w6L-Q6JCl6YOoIiwiaXAiOiI1OS4xNzIuMTM3Ljg3IiwiY29tcGFueU5hbWUiOiLogZTmipXpm4blm6It5bmz5Y-w6L-Q6JCl6YOoIiwiZGVwdElkIjowLCJ1c2VyTmFtZSI6ImFkbWluIiwidXNlcklkIjoiMSIsIm9yZ0lkIjoxLCJyYW5kb20iOiIxNzA2NTIyNzEwMDkxIiwiYXVkIjoiIiwiY29tcGFueUlkIjoxLCJleHAiOjE3MDY1MjQ1MTB9.bVa5hH1PdZDnq9USJWW8OB_4JkW3xEtEzzJl-f0a3c0");

        post.setRest(true);

        post.body("");

        int count = 0;

        for (String param : getParams()) {
            long startTime = System.currentTimeMillis();
            post.body(param);

            try (HttpResponse response = post.execute()) {

                System.out.println(response.body());

                log.info("第{}次调用,状态:{},耗时:{}ms" , ++count , response.getStatus() , System.currentTimeMillis() - startTime);
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> getParams() {

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