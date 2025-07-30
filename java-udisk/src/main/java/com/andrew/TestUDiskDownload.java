package com.andrew;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class TestUDiskDownload {

    public static void main(String[] args) throws IOException {

        System.out.println(DateUtil.format(new Date(),"yyyyMMddHHmmSS"));


        Collection<TestBean> resultList = new ArrayList();

        resultList.add(new TestBean("name1", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name2", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name3", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name4", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name5", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name6", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name7", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name8", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name9", "code1", 1, new BigDecimal(1.1), "desc1"));
        resultList.add(new TestBean("name10", "code1", 1, new BigDecimal(1.1), "desc1"));

        String fileName = "测试数据集" + "_" + System.currentTimeMillis() + ".xlsx";

        ExcelWriter writer = ExcelUtil.getWriter();
        Object headElement = resultList.toArray()[0];
        Set<String> props = new HashSet<>();
        Field[] fields = headElement.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            props.add(field.getName());
        }
        writer.writeHeadRow(props);

        for (Object row : resultList) {
            writer.writeRow(row, false);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        writer.flush(os);

        try {
            saveFile(fileName, os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            os.close();
            writer.close();
        }
    }

    public static String saveFile(String fileName, byte[] contents) throws IOException {
        // 执行 lsblk 命令来列出块设备
        String mountPath = getMountPath();

        log.info("找到 U 盘挂载点: {}", mountPath);

        // 在U盘下创建 fire-download 目录
        File dir = new File(mountPath, "fire_download/" + fileName);
        FileUtil.touch(dir);

        try (FileOutputStream fos = new FileOutputStream(dir)) {
            fos.write(contents);
            fos.flush();
            fos.getFD().sync();
        }

        log.info("文件已写入：{}", dir.getAbsolutePath());

        return dir.getAbsolutePath();
    }

    private static String getMountPath() throws IOException {
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", "sudo lsblk -o NAME,RM,MOUNTPOINT -nr"});
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String mountPath = null;
        String line;
        while ((line = reader.readLine()) != null) {
            // 输出示例: sda1 1 /media/ubuntu/Ventoy
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                String rm = parts[1];         // 是否是可移动设备
                String mount = parts[2];      // 挂载点
                if ("1".equals(rm) && !mount.isEmpty()) {
                    mountPath = mount;
                    break;  // 找到第一个可用U盘就停止
                }
            }
        }

        if (mountPath == null) {
            throw new IllegalStateException("保存文件至USB失败,未找到挂载的 U 盘.");
        }
        return mountPath;
    }
}
