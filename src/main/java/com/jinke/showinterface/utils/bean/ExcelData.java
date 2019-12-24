package com.jinke.showinterface.utils.bean;

import java.util.List;

import lombok.Data;

@Data
public class ExcelData {
	private int sheetSum;
    private String fileName;
    private List<ExcelSheetData> sheetData;
}
