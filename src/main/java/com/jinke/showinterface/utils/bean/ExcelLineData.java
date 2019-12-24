package com.jinke.showinterface.utils.bean;

import java.util.List;
import lombok.Data;

@Data
public class ExcelLineData {
	/**
     * 行编号
     */
    private int lineNumber;
    /**
     * 行总列数
     */
    private int colSum;
    /**
     * 列数据集合
     */
    private List<String> colData;
}
