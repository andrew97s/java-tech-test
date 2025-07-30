package com.andrew;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @author tongwenjin
 * @since 2025-7-25
 */

@Slf4j
public class JsonParser {

    public static void main(String[] args) {

        String str = FileUtil.readString("C:\\Users\\Administrator\\Desktop\\event.txt", Charset.defaultCharset());
        JSONObject json = JSON.parseObject(str);

        JSONArray datas = json.getJSONArray("data");
        for (int i = 0; i < datas.size(); i++) {
            JSONObject data = datas.getJSONObject(i);
            JSONArray eventList = data.getJSONArray("eventList");

            for (int j = 0; j < eventList.size(); j++) {
                JSONObject event = eventList.getJSONObject(j);

//                log.info("{} - {}" ,event.getString("id") , event.getString("name"));
                log.info("{}" ,event.getString("id") );

            }
        }

        System.out.println(json);
    }
}
