package com.andrew.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tongwenjin
 * @since 2024/12/11
 */
public class PatternTest {
    public static void main(String[] args) {
        String json = "{\n" +
                "    \"name\": \"Alice\",\n" +
                "    \"profile\": {\n" +
                "        \"photos\": {\n" +
                "            \"imageUrl\": \"http://example.com/photo.jpg\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"extra\": {\n" +
                "        \"details\": {\n" +
                "            \"unusedKey\": \"someValue\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // 定义正则表达式匹配 imageUrl
        String regex = "\"imageUrl\"\\s*:\\s*\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            // 提取第一个匹配的值
            String imageUrl = matcher.group(1);
            System.out.println("Image URL: " + imageUrl);
        } else {
            System.out.println("Image URL not found");
        }
    }
}
