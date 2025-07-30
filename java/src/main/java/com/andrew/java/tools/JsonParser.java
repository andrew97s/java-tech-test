package com.andrew.java.tools;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;

import java.nio.charset.Charset;

/**
 * @author tongwenjin
 * @since 2025-7-25
 */
public class JsonParser {

    public static void main(String[] args) {

        String json = FileUtil.readString("C:\\Users\\Administrator\\Desktop\\event.txt", Charset.defaultCharset());
//        JSON.parseObject(json)

        System.out.println(json);
    }
}
