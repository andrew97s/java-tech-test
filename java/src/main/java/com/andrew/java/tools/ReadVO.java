package com.andrew.java.tools;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author tongwenjin
 * @since 2024/1/11
 */
@Data
public class ReadVO {
    @ExcelProperty("资质名称")
    private String qualName;

    @ExcelProperty("name1")
    private String name1;

    @ExcelProperty("name2")
    private String name2;

    @ExcelProperty("name3")
    private String name3;

    @ExcelProperty("fileId")
    private String fileId;
}
