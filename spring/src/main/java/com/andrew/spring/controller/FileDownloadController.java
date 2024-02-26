package com.andrew.spring.controller;

import cn.hutool.core.io.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author tongwenjin
 * @since 2024/2/26
 */

@RestController
@RequestMapping("file/download")
public class FileDownloadController {

    public static final String BASE_PATH = "D:\\data\\ebs-lib\\lic";

    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName , HttpServletResponse response) {

        response.setHeader( "Content-Type","text/plain; charset=utf-8");
        try(ServletOutputStream os = response.getOutputStream()) {
            os.write(FileUtil.readBytes(new File(BASE_PATH + "/" + fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
