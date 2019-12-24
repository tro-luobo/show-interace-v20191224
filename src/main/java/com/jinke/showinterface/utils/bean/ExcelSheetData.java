package com.jinke.showinterface.utils.bean;


import java.util.List;

import lombok.Data;

@Data
public class ExcelSheetData {
	/**
     * 工作簿名称
     */
    private String sheetName;
    /**
     * 表格总行数
     */
    private int lineSum;
    /**
     * 行数据集合
     */
    private List<ExcelLineData> lineData;
}
