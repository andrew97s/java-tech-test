package com.andrew.java.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The type File download task.
 *
 * @author tongwenjin
 * @since 2024 /1/9
 */
@Slf4j
public class FileDownloadTask {
    private static final String DETAIL_URL = "http://10.30.8.30:6801/base/file/list?ids=";
    private static final String DOWNLOAD_URL = "http://10.30.8.30:6801/base/file/getById/";

    private static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJkZXB0TmFtZSI6IiIsInByZWZlY3R1cmVDb2RlIjoibnVsbCIsIm9yZ05hbWUiOiLogZTmipXpm4blm6It5bmz5Y-w6L-Q6JCl6YOoIiwiaXAiOiIxMTcuMTU0LjEwMC4xMzEiLCJjb21wYW55TmFtZSI6IuiBlOaKlembhuWboi3lubPlj7Dov5DokKXpg6giLCJkZXB0SWQiOjAsInVzZXJOYW1lIjoiYWRtaW4iLCJ1c2VySWQiOiIxNTgxNjI4NTQ1NDY1MTU5NjgxIiwib3JnSWQiOjEsInJhbmRvbSI6IjE3MDg2NzM0NTUwNzgiLCJhdWQiOiIiLCJjb21wYW55SWQiOjEsImV4cCI6MTcwODY3NTI1NX0.oR-7w7c2Bw4zRbfyz87YY39jEs5H2YLpStMFioGvJME";

    private static final String TEMP_DOWNLOAD_DIR = "/data/uploadDir/qual/";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws Exception {
        querySupFileFromDb();
    }


    /**
     * 查询供应商文件信息
     */
    public static void querySupFileFromDb() throws SQLException {
        String baseFilePath = "D:\\供应商基本信息文件";
        // 营业执照、 安全许可证、 近三年财报、管理人员表、近三年业绩、业绩证明材料
        DataSource datasource = getDataSource("pro");
        // 查询数据
        List<Entity> fileInfos = DbUtil.use(datasource).query("" +
                "select boi.id,\n" +
                "       boi.org_name,\n" +
                "       reg_addr_province,\n" +
                "       org_license_file_id,\n" +
                "       safe_file_id,\n" +
                "       finance_file_id,\n" +
                "       person_info_file_id,\n" +
                "       achievement_file_id,\n" +
                "       achievement_prove_file_id\n" +
                "from base_organization_info boi\n" +
                "         left join base_organization bo on boi.organization_id = bo.id\n" +
                "where bo.is_deleted = 0\n" +
                "  and boi.is_deleted = 0\n" +
                "  and bo.status = 80\n" +
                "group by organization_id limit 500\n");
        // 查询省市值集
        Map<String, String> provinceMap = DbUtil.use(datasource)
                .query("select val , name from base_valset where code = 'PROVINCES_NAME' and val != '' and is_deleted = 0")
                .stream().collect(Collectors.toMap(ite -> (String) ite.get("val"), ite -> (String) ite.get("name")));

        Integer count = 0;
        for (Entity fileInfo : fileInfos) {
            if (count % 400 == 0) {
                TOKEN = getToken("pro");
                log.info("重新获取token:{}", TOKEN);
            }
            String orgName = (String) fileInfo.get("org_name");
            String provinceCode = (String) fileInfo.get("reg_addr_province");
            String provinceName = provinceMap.get(provinceCode);
            String licenseFileId = (String) fileInfo.get("org_license_file_id");
            String safeFileId = (String) fileInfo.get("safe_file_id");
            String financeFileId = (String) fileInfo.get("finance_file_id");
            String personInfoFileId = (String) fileInfo.get("person_info_file_id");
            String achFileId = (String) fileInfo.get("achievement_file_id");
            String achProvFileId = (String) fileInfo.get("achievement_prove_file_id");

            // 脏数据 - 直接跳过
            if (StrUtil.isBlank(provinceName)) {
                log.error("发现异常省份数据:{} , {}", orgName, provinceCode);
                continue;
            }
            log.info("发现第 {}(共{}个) 个 供应商信息 : {} , {}", count++, fileInfos.size(), fileInfo.get("org_name"), provinceName);

            // 分别保存对应的文件
            execDownload(licenseFileId, baseFilePath + "\\营业执照\\" + provinceName + "-" + orgName);
            execDownload(safeFileId, baseFilePath + "\\安全许可证\\" + provinceName + "-" + orgName);
            execDownload(financeFileId, baseFilePath + "\\近三年财报\\" + provinceName + "-" + orgName);
            execDownload(personInfoFileId, baseFilePath + "\\管理人员\\" + provinceName + "-" + orgName);
            execDownload(achFileId, baseFilePath + "\\近三年业绩\\" + provinceName + "-" + orgName);
            execDownload(achProvFileId, baseFilePath + "\\业绩证明\\" + provinceName + "-" + orgName);
        }

    }

    public static void queryExpTitleFileFromDb() throws Exception {
        String baseFilePath = "D:\\专家职称文件";
        DataSource datasource = getDataSource("pro");

        List<Entity> expList = DbUtil.use(datasource).query("select le.id, eb.title_file , eb.job_title, eb.name\n" +
                "from ess_lib_experts_base eb\n" +
                "         left join ess_lib_experts le on eb.id = le.lib_experts_base_id\n" +
                "where eb.is_deleted = 0\n" +
                "  and le.is_deleted = 0\n" +
                "  and le.expert_status = 80\n" +
                "  and eb.title_file is not null\n" +
                "  and eb.title_file != '';");


        Map<String, String> titleNameMap = DbUtil.use(datasource)
                .query("\tselect name, val from base_valset where code = 'TECHNICAL_TITLE' and is_deleted = 0 and val != '';")
                .stream().collect(Collectors.toMap(ite -> (String) ite.get("val"), ite -> (String) ite.get("name")));

        int count = 0;
        for (Entity exp : expList) {
            if (count % 400 == 0) {
                TOKEN = getToken("pro");
                log.info("重新获取token:{}", TOKEN);
            }
            String name = (String) exp.get("name");
            String jobTitle = (String) exp.get("job_title");
            String titleFile = (String) exp.get("title_file");

            String[] titleNames = jobTitle.split(",");
            StringBuilder titleName = new StringBuilder();
            for (String nameCode : titleNames) {
                titleName.append(titleNameMap.get(nameCode)).append("-");
            }
            String[] fileIds = titleFile.split(",");
            log.info("发现第 {}(共{}个) 个 专家信息 : {}", count++, expList.size(), name);
            Integer fileIndex = 1;
            for (String fileId : fileIds) {
                if (fileIds.length > 1) {
                    execDownload(fileId, baseFilePath + "/" + name + "_" + titleName + fileIndex++);
                } else {
                    execDownload(fileId, baseFilePath + "/" + name + "_" + titleName + fileIndex++);
                }
            }
        }
    }

    /**
     * 重命名供应商资质文件
     */
    public static void renameFile() {
        //        String directory = "/data/uploadDir/qual";
        String directory = "C:\\Program Files\\feiq\\Recv Files\\qual\\data\\uploadDir\\qual";

        HashMap<String, String> codeName = new HashMap<>();
        codeName.put("10", "工程勘察");
        codeName.put("100", "审计");
        codeName.put("110", "会计");
        codeName.put("120", "评估");
        codeName.put("130", "法务");
        codeName.put("140", "税务");
        codeName.put("150", "其他");
        codeName.put("160", "爆破作业");
        codeName.put("20", "工程设计");
        codeName.put("30", "建筑业企业资质(施工总承包/专业承包)");
        codeName.put("40", "工程监理");
        codeName.put("50", "工程招标代理");
        codeName.put("60", "设计施工一体化");
        codeName.put("70", "工程造价咨询");
        codeName.put("80", "劳务");
        codeName.put("90", "家具");

        // 获取当前目录的路径
        Path currentDir = Paths.get(directory);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDir)) {
            // 遍历当前目录下的所有文件
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    System.out.println(path.getFileName());

                    String fileName = path.getFileName().toString();
                    if (fileName.contains("_")) {
                        String fileType = fileName.substring(0, fileName.indexOf("_"));

                        String fileTypeName = codeName.get(fileType).replaceAll("/", "-");

                        String newFileName = fileTypeName + fileName.substring(fileName.indexOf("_"));

                        // 确保目标文件的父目录存在
                        Path newPath = Paths.get(directory + "/" + fileTypeName + "/" + newFileName);
                        Files.createDirectories(newPath.getParent());
                        Files.move(path, newPath);

                        log.info("移动{}至 {} !", path.getFileName(), newPath.getFileName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询供应商资质文件信息
     */
    public static void queryQualFromDb() {
        AtomicInteger count = new AtomicInteger(0);

        DataSource dataSource = getDataSource("pro");

        try {
            List<Entity> type1List = DbUtil.use(dataSource).query("select val , name from base_valset where code = 'QUALIFICATION_TYPE' and is_deleted = 0");
            Map<String, String> type1Map = type1List.stream().filter(ite -> ite.get("val") != null && ite.containsKey("val")).collect(Collectors.toMap(ite -> (String) ite.get("val"), ite -> (String) ite.get("name")));

            List<Entity> qualList = DbUtil.use(dataSource).query("select file_id , qual_seq , qual_name from mbd_lib_sup_qualification where file_id is not null and status = 80 and is_deleted = 0");
            System.out.println(type1Map);
            List<ReadVO> readVOList = qualList.stream().map(ite -> {
                ReadVO readVO = new ReadVO();
                readVO.setFileId(String.valueOf(ite.get("file_id")));
                readVO.setQualName(String.valueOf(ite.get("qual_name")));
                readVO.setName2(type1Map.get(String.valueOf(ite.get("qual_seq"))));
                readVO.setName1(String.valueOf(ite.get("qual_seq")));

                execDownload(readVO);

                count.addAndGet(1);

                return readVO;
            }).collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info("Read completed,total : {} !", count.get());
    }

    private static void execDownload(String fileId, String filePath) {
        if (StrUtil.isBlank(fileId)) {
            log.warn("文件ID为空,放弃执行下载!");
            return;
        }
        HttpRequest request = HttpUtil.createGet(DOWNLOAD_URL);

        request.setUrl(DETAIL_URL + fileId);

        // 获取文件真实类型
        HashMap<String, String> nameMap = new HashMap<>();
        try (HttpResponse response = request.execute()) {
            JSONObject resp = JSONUtil.parseObj(response.body());
            JSONArray detailList = resp.getJSONArray("data");
            for (Object item : detailList) {
                JSONObject detail = (JSONObject) item;
                nameMap.put(detail.getStr("id"), detail.getStr("fileType"));
            }
        }

        request.setUrl(DOWNLOAD_URL + fileId + "/aa.txt");
        // 设置文件全路径名称
        String fileName = filePath + "." + nameMap.get(fileId);
        FileUtil.mkParentDirs(fileName);
        try (
                HttpResponse response = request.execute();
                FileOutputStream responseFile = new FileOutputStream(fileName)
        ) {
            log.info("response code {}", response.getStatus());
            responseFile.write(response.bodyBytes());
            log.info("完成保存{}", fileName);
        } catch (Exception e) {
            log.error("执行HTTP请求出现异常,msg:{}", e.getMessage());
        }
    }

    private static void execDownload(ReadVO map) {
        if (StrUtil.isBlank(map.getName1())) {
            log.warn("资质类型名称为空,跳过!");
            return;
        }
        HttpRequest request = HttpUtil.createGet(DOWNLOAD_URL);

        request.header("token", TOKEN);

        String fileIdStr = map.getFileId();
        request.setUrl(DETAIL_URL + fileIdStr);

        HashMap<String, String> nameMap = new HashMap<>();

        try (HttpResponse response = request.execute()) {
            JSONObject resp = JSONUtil.parseObj(response.body());
            JSONArray detailList = resp.getJSONArray("data");
            for (Object item : detailList) {
                JSONObject detail = (JSONObject) item;
                nameMap.put(detail.getStr("id"), detail.getStr("fileType"));
            }
        }

        for (String fileId : map.getFileId().split(",")) {
            request.setUrl(DOWNLOAD_URL + fileId);
            String fileName = TEMP_DOWNLOAD_DIR + getFileName(map) + "_" + System.currentTimeMillis() + "." + nameMap.get(fileId);
            try (
                    HttpResponse response = request.execute();
                    FileOutputStream responseFile = new FileOutputStream(fileName)
            ) {
                log.info("response code {}", response.getStatus());
                responseFile.write(response.bodyBytes());
                log.info("完成保存{}", fileName);
            } catch (Exception e) {
                log.error("执行HTTP请求出现异常,msg:{}", e.getMessage());
            }
        }
    }

    public static String getToken(String env) {


        if ("pro".equals(env)) {
            String result = HttpUtil.post(
                    "http://10.30.8.30:6801/base/user/login",
                    "{\"loginType\":1,\"username\":\"admin\",\"random\":\"\",\"code\":\"\",\"xstr\":\"TIH46AiWp3kQFBc9SmBgGkBW0VEnyD/beyrvK4hvXxKfBpuBgRsjAgN+eMVoCf6RMHqjgsl2HIj316nBm+ov+4UL5/t/2N7aCRyAAvXtnn2bAR6eBeUgVABXFL975PHGpj/xAAj6EtazZzbp4zFxT4Ci3SnpQYuTtRCqf1rZDHE=\"}");

            JSONObject response = new JSONObject(result);

            return (String) ((JSONObject) response.get("data")).get("token");
        } else {
            String result = HttpUtil.post(
                    "http://58.49.42.131:2100/ebidding/api/base/user/login",
                    "{\"loginType\":1,\"username\":\"cs.pm001\",\"random\":\"\",\"code\":\"\",\"xstr\":\"YqnaRJHJJoxlviRHQAfwMYUboIveQg9TxeeBMQiYEVtkLBqyEsYdhS+gTNTAcaj2X4a0htXnDjg/EDgNKaaeZrtNzaiW+vZYWJPVcy9wP+GnrTiLwPy3OF6DU915Cbn6eef0YaZZStSwRZGA+X91lWFIpsqQP3AbFhvGO4N6erg=\"}");

            JSONObject response = new JSONObject(result);

            return (String) ((JSONObject) response.get("data")).get("token");
        }
    }

    private static DataSource getDataSource(String env) {

        if (env.equals("pro")) {
            return DSFactory.create(
                    Setting.create()
                            .set("url", "jdbc:mysql://10.30.8.34:13346/hblt_ebidding_prod?characterEncoding=utf8")
                            .set("username", "devops")
                            .set("password", "le6cjzo3mxx16Fs")
            ).getDataSource();
        } else {
            return DSFactory.create(
                    Setting.create()
                            .set("url", "jdbc:mysql://58.49.42.131:13346/hblt_ebidding_uat?characterEncoding=utf8")
                            .set("username", "root")
                            .set("password", "1234_rfv")
            ).getDataSource();
        }
    }

    private static String getFileName(ReadVO map) {
        return (StrUtil.isNotBlank(map.getName3()) ? map.getName3() : map.getName1()).replaceAll("/", "-");
    }
}
