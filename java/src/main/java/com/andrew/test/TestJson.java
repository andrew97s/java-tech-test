package com.andrew.test;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.nio.charset.StandardCharsets;

/**
 * @author tongwenjin
 * @since 2025-3-10
 */
public class TestJson {
    public static void main(String[] args) {
        String content = FileUtil.readString("C:\\Users\\Administrator\\Desktop\\aa.txt", StandardCharsets.UTF_8);


        System.out.println(content);
        JSON.parseArray(content).forEach(item->{
            JSONObject object = (JSONObject) item;

            System.out.println(((JSONObject) item).getString("resourceName"));
        });
    }
}
