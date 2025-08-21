package com.zhian;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * The type Net server.
 *
 * @author tongwenjin
 * @since 2025 -6-25
 */
@Slf4j
public class NetServer {

    private static String PUSH_URL;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        Assert.notEmpty(args, "请提供推送URL");
        PUSH_URL = args[0];

        // 创建 HttpServer 绑定到 9200 端口
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 9200), 0);

        // 创建上下文，路径为 /
        server.createContext("/", new JbHttpHandler());

        // 启动线程池执行（也可以使用默认 executor）
        server.setExecutor(null); // 默认 executor
        server.start();

        log.info("HTTP Server started on port 9200! , pushUrl: {}", PUSH_URL);
    }


    static class JbHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "{\"code\":200,\"msg\":\"ok\"}";

            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();
            log.info("request arrived: {}", path);

            // 读取请求体
            if (method.equals("POST")) {
                try (InputStream is = exchange.getRequestBody()) {
                    String requestBody = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .lines()
                            .reduce("", (acc, line) -> acc + line);
                    log.info("request body : {}", requestBody);
                    // 处理消息
                    CompletableFuture.runAsync(() -> processMsg(requestBody)).exceptionally(e -> {
                        log.error("process error : {}", e.getMessage());
                        return null;
                    });
                }
            } else if (path.equals("/api/device/list")) {
                response = handleFetchDevReq(query);
            }

            // 响应
            try (OutputStream os = exchange.getResponseBody();) {
                try {
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    os.write(response.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("write completed");
            }
        }
    }

    private static void processMsg(String source) {
        if (StrUtil.isBlank(source)) {
            return;
        }
        // TODO 解析格式 & 执行推送
        MonitorMsg monitorMsg = JSONObject.parseObject(source, MonitorMsg.class);
        Facility mf = monitorMsg.getFacility();
        if (mf == null || StrUtil.isEmpty(mf.getAddrStr())) {
            throw new RuntimeException("数据格式有误");
        }
        // 网关编码自动转小写
        else {
            if (mf.getAddrStr().equals(mf.getNet())) {
                mf.setAddrStr(mf.getAddrStr().toLowerCase());
            }
            mf.setNet(StrUtil.isNotBlank(mf.getNet()) ? mf.getNet().toLowerCase() : "");
        }
        String fsn = mf.getAddrStr() == null ? null : mf.getAddrStr().trim();

        Integer chn = null;
        if (fsn.endsWith("通道")) {
            chn = Integer.parseInt(fsn.substring(fsn.indexOf(' ') + 1).substring(0, 1));
        }
        if (fsn.contains(" 线路") && mf.getNet() != null && fsn.startsWith(mf.getNet())) {
            chn = Integer.parseInt(fsn.substring(fsn.indexOf("线路") + 2));
        }
        // 剔除 【通道 、 线路】 关键字
        if (fsn.contains("通道") || fsn.contains("线路")) {
            fsn = fsn.substring(0, fsn.lastIndexOf(' ')).trim();
        }


        MqMessage.Facility facility = new MqMessage.Facility();
        facility.setCode(fsn);
        facility.setOnLine(true);
        facility.setWireless(mf.isWireless());
        facility.setName(mf.getDescr());
        facility.setNet(mf.getNet());
        facility.setChn(chn);
        facility.setModel(StrUtil.isEmpty(mf.getFacilitiesModel()) ? mf.getModel() : mf.getFacilitiesModel());
        facility.setType(mf.getFacilitiesTypeCode().toString());
        if (StrUtil.isNotEmpty(mf.getRssi())) {
            facility.setRssi(Integer.parseInt(mf.getRssi()));
        }
        if (StrUtil.isNotEmpty(mf.getTemperature())) {
            facility.setTemperature(Integer.parseInt(mf.getTemperature()));
        }
        if (StrUtil.isNotEmpty(mf.getVoltage())) {
            facility.setVoltage(Integer.parseInt(mf.getVoltage()));
        }

        MqMessage mqMessage = new MqMessage();
        mqMessage.setDeviceId(-1L);
        mqMessage.setEvent(monitorMsg.getEvent().equalsIgnoreCase("alarm") ? MqMessage.EVENT_ALARM : MqMessage.EVENT_BUSINESS);
        mqMessage.setFacility(facility);
        mqMessage.setMsgData(JSONObject.toJSONString(monitorMsg));
        mqMessage.setTime(new Date());
        mqMessage.setProtocol("jb");
        mqMessage.setUuid(SnowflakeIdWorker.getInstance().nextStringId());

        String post = HttpUtil.post(PUSH_URL, JSON.toJSONString(mqMessage));

        log.info("resp : {}", post);
    }

    private static String handleFetchDevReq(String query) {

        Map<String, String> queryMap = parseQueryParams(query);
        int pageNum = NumberUtil.isNumber(queryMap.get("pageNum")) ? Integer.parseInt(queryMap.get("pageNum")) : 1;
        int pageSize = NumberUtil.isNumber(queryMap.get("pageSize")) ? Integer.parseInt(queryMap.get("pageSize")) : 100;

        ExcelReader reader = ExcelUtil.getReader("/userdata/zhian/tr_server_all.xlsx");

        int rowCount = reader.getRowCount();
        List<List<Object>> data = reader.read((pageNum - 1) * pageNum + 1, pageSize);

        List<ZaSysDevice> devList = Collections.emptyList();

        if (CollUtil.isNotEmpty(data)) {
            devList = data.stream().map(item -> {
                ZaSysDevice dev = new ZaSysDevice();
                dev.setCode(item.get(1).toString());
                dev.setType(item.get(0).toString());
                dev.setName(item.get(2).toString());
                dev.setNet(item.get(3).toString());
                dev.setModel(item.get(4).toString());
                try {
                    dev.setLongitude(
                            item.get(5) != null ?
                                    BigDecimal.valueOf(Double.parseDouble(item.get(5).toString())) : null
                    );
                    dev.setLatitude(
                            item.get(6) != null ?
                                    BigDecimal.valueOf(Double.parseDouble(item.get(6).toString())) : null
                    );
                } catch (Exception e) {
                }
                dev.setWireless("是".equals(item.get(7).toString()) ? "1" : "0");
                dev.setPfCode(item.get(8).toString());

                return dev;
            }).collect(Collectors.toList());
        }

        HashMap<String, Object> result = new HashMap<>();

        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("rows", devList);
        result.put("total", rowCount - 1);

        log.info("data : {}", JSON.toJSONString(data));

        return JSON.toJSONString(result);
    }

    public static Map<String, String> parseQueryParams(String url) {
        Map<String, String> params = new HashMap<>();
        try {
            if (url != null) {
                for (String param : url.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = pair.length > 1 ?
                            URLDecoder.decode(pair[1], "UTF-8") : "";
                    params.put(key, value);
                }
            }
        } catch (Exception e) {
        }
        return params;
    }

    public static String addressFormat(String input) {
        // 去除所有空格
        input = input.replaceAll("\\s+", "");

        // 正则提取：例如 4机1-1 -> 4, 1, 1
        return input.replaceAll("(\\d+)机(\\d+)-(\\d+)", "$1-$2-$3");
    }
}
