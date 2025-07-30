package com.andrew.java.tools;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tongwenjin
 * @since 2025-1-23
 */
@Slf4j
public class FetchAmount {
    public static void main(String[] args) {
        String startDate = "2001-01-01";
        String endDate = "2001-07-01";
        while (true) {
            String url = "https://my.pay.qq.com/cgi-bin/personal/account_tradeinfo.cgi?coin_type=0&start_date=" +startDate +
                    "&end_date="+endDate+"&page_no=1&channel=all&per=5&extcode=&t=0.3135803203248939&openid=A9A3BD6342A2F85AFC1D108C4CAB4E8D&openkey=D5AA6A49C24E43A0560275FCD5559D8A&session_id=openid&session_type=kp_accesstoken&qq_appid=101502376";
            HttpRequest req = HttpUtil.createGet(url);
            req.header("Cookie" , "RK=sqlNzQv2Hm; ptcz=bdb36de303940d5166f5d29fc73e385b9235a35e2dd734e4bc254f90492eb0b8; _qpsvr_localtk=0.7937682224002713; pay_mobile_account=qq; pay_mobile_game_account=; pay_qq_appid=101502376; uin=o1445481863; skey=@b3dSXoBbI; pgv_info=ssid=s296508391; ts_refer=my.pay.qq.com/; pgv_pvid=5826471304; ts_uid=921207039; midas_cookie_samesite_flag=1; midas_openid=1445481863; midas_openkey=@b3dSXoBbI; pay_openid=A9A3BD6342A2F85AFC1D108C4CAB4E8D; pay_openkey=D5AA6A49C24E43A0560275FCD5559D8A; pay_session_id=openid; pay_session_type=kp_accesstoken; pay_qq_avatar=https://thirdqq.qlogo.cn/ek_qqapp/AQJibdgW5u5jq7sqiaovfficJygSmicC2QOF1rAnwoRdTpXtyfAuOzYCH0zS3zUstvZoTc1xcVqD/100; pay_qq_nickname=Andrew; pay_sessionid=7F85C7E465102785B5B5DE444413AF91; tgw_l7_route=b94f7f45f5ed4af19a84b4c103ca0bd0; verifysession=h01b9aa1d2319430a9a491b670e627d482515a57c06a30fadc66ce3dec7c17cdeabb55d725d00594956");

            String response = req.execute().body();

            JSONObject resp = JSONUtil.parseObj(response);
            JSONObject resultInfo = resp.getJSONObject("resultinfo");

            JSONArray list = resultInfo.getJSONArray("list");

            for (Object item : list) {
                if (item instanceof JSONObject) {
                    log.info(((JSONObject)item).getStr("tranday") +  " : " + ((JSONObject)item).getStr("info"));
                }
            }
            if (startDate.endsWith("01-01")) {
                startDate = startDate.replace("-01-01", "-07-01");
                endDate = endDate.replace("-07-01", "-12-31");
            }
            else {
                int year = Integer.parseInt(startDate.substring(0, 4));

                int newYear = year + 1;
                startDate = startDate
                        .replace(year + "", (newYear) + "")
                        .replace("-07-01", "-01-01");
                endDate = endDate
                        .replace(year + "", (newYear) + "")
                        .replace("-12-31", "-07-01");
            }

            if (startDate.startsWith("2026")) {
                break;
            }
        }


    }
}
