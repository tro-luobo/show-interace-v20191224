package com.jinke.showinterface.utils;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.alibaba.fastjson.JSON;
import com.jinke.showinterface.entity.CheckWorkEntity;
import com.jinke.showinterface.utils.bean.ExcelData;
import com.jinke.showinterface.utils.bean.ExcelLineData;
import com.jinke.showinterface.utils.bean.ExcelSheetData;

public class ExcelUtil {
    private static XSSFWorkbook wb;
    private static XSSFSheet sheet;
    private static XSSFRow row;
 
    /**
     * 获取多个sheetExcel表格数据
     *
     * @param fileName Excel 数据表格
     * @return
     */
    public ExcelData readMultiSheetExcel(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        ExcelData excelData = new ExcelData();
        try {
            wb = new XSSFWorkbook(is);
	        Integer sheetNum = wb.getNumberOfSheets();
	        excelData.setSheetSum(sheetNum);
	        excelData.setFileName(file.getName());
	 
	        //循环获取所有sheet数据
	        List<ExcelSheetData> sheetDatas = new ArrayList<>();
	        for (int i = 0; i < sheetNum; i++) {
	            ExcelSheetData sheetData = new ExcelSheetData();
	            sheet = wb.getSheetAt(i);
	            sheetData.setLineSum(sheet.getPhysicalNumberOfRows());
	            sheetData.setSheetName(sheet.getSheetName());
	 
	            List<ExcelLineData> lineDatas = readExcelContentBySheet(sheet);
	            sheetData.setLineData(lineDatas);
	            sheetDatas.add(sheetData);
	        }
	        excelData.setSheetData(sheetDatas);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
				//wb.close();
                if(is != null){
                    is.close();
                }

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return excelData;
    }
 
    private List<ExcelLineData> readExcelContentBySheet(XSSFSheet sheet2) {
        List<ExcelLineData> lineDatas = new ArrayList<>();
        // 得到总行数
        int rowNum = sheet2.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            int j = 0;
            row = sheet2.getRow(i);
            if (Objects.isNull(row)) {
                continue;
            }
 
            int colNum = row.getPhysicalNumberOfCells();
            ExcelLineData lineData = new ExcelLineData();
            List<String> colData = new ArrayList<>();
            lineData.setColSum(colNum);
            while (j < colNum) {
                String value = getCellValue(row.getCell((short) j)).trim();
                colData.add(value);
                j++;
            }
            lineData.setColData(colData);
            lineDatas.add(lineData);
        }
 
        return lineDatas;
    }
 
    /**
     * 获取单元格数据
     *
     * @param xssfCell Excel单元格
     * @return String 单元格数据内容
     */
    private String getCellValue(XSSFCell xssfCell) {
        if (Objects.isNull(xssfCell)) {
            return "";
        }
 
        String value = "";
        switch (xssfCell.getCellType()) {
            case NUMERIC: // 数字
                //如果为时间格式的内容
                if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(xssfCell.getNumericCellValue());
                }
                break;
            case STRING: // 字符串
                value = xssfCell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                value = xssfCell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                value = xssfCell.getCellFormula() + "";
                break;
            case BLANK: // 空值
                value = "";
                break;
            case ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }
    
    public static void main(String[] args) throws ParseException {
    	String oldPath = "C:\\Users\\DELL\\Desktop\\金科工作资料\\打卡数据\\data";
		String newdPath = "C:\\Users\\DELL\\Desktop\\金科工作资料\\打卡数据\\import";
    	File file = new File(oldPath);
    	//清空临时数据
    	deleteFiles(new File(newdPath));
    	LinkedList<File> fileList = new LinkedList<File>();
        if (file.exists()) {
        	int x = 0;
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (!file2.isDirectory()) {
                	System.out.println("原始文件:" + file2.getName());
                	System.out.println("新文件:" + newdPath + "\\" + x + ".xlsx");
                    file2.renameTo(new File(newdPath + "\\" + x + ".xlsx"));
                    fileList.add(file2);
                    x++;
                }
            }
        }
        
        // 多线程处理
        int oneThradeSize = 2;
        //开启线程数量
        int count = fileList.size();
        int pageCount = count%oneThradeSize==0?(count/oneThradeSize):(count/oneThradeSize)+1;
        try {
	        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
	        ExecutorService executor = Executors.newFixedThreadPool(pageCount);
	        for(int i = 0 ;i < pageCount; i++) {
	        	int form = i*oneThradeSize;
	        	int to = i * oneThradeSize + oneThradeSize;
	        	LinkedList<File> newFileList = new LinkedList<>();
	        	for(int j = form; j<to; j++) {
	        		if(to <= count) {
	        			newFileList.add(fileList.get(j));
	        		}
	        	}
	        	ThradeTaskImprotExcel task = null; //new ThradeTaskImprotExcel(newFileList);
	        	executor.execute(task);
	        	System.out.println("线程"+i+"已启动："+ task.currentThread().getName());
	        }
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    class ThradeTaskImprotExcel extends Thread{
    	private LinkedList<File> fileList;
    	
    	ThradeTaskImprotExcel(LinkedList<File> fileList){
    		this.fileList = fileList;
    	}
    	
    	public void improt() throws ParseException {
    		ExcelUtil excelReader = new ExcelUtil();
    		for(File file:fileList) {
                ExcelData excelData = excelReader.readMultiSheetExcel(file);
                for(ExcelSheetData esd : excelData.getSheetData()) {
                	List<ExcelLineData> list = esd.getLineData();
                	int i = 0;
                	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                	for(ExcelLineData el : list) {
                		 if(i > 2) {
                			 String name = el.getColData().get(0);
                			 String bm = el.getColData().get(1);
                			 String gh = el.getColData().get(2);
                			 String zw = el.getColData().get(3);
                			 String uid = el.getColData().get(4);
                			 String rq = el.getColData().get(5);
                			 String sbtime = el.getColData().get(6);
                			 String xbtime = el.getColData().get(7);
                			 if(StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(gh) && StringUtils.isNotBlank(sbtime) && StringUtils.isNotBlank(rq)) {
                				 CheckWorkEntity work = new CheckWorkEntity();
                				 work.setUserCode(gh);
                				 
                				 String syymmdd = rq.substring(0, 8);
                				 String sDate = "20"+syymmdd + " " + sbtime;
                				 Date stime = format.parse(sDate);
                				 work.setStartDate(stime);
                				 
                				 String eyymmdd = rq.substring(0, 8);
                				 if(StringUtils.isNoneBlank(xbtime)) {
                					 if(xbtime.substring(0,2).equals("次日")) {
                						int day = Integer.parseInt(eyymmdd.substring(6, 8));
                						day = day + 1;
                						if(day < 10) {
                							eyymmdd = eyymmdd.substring(0,6) + "0" + day;
                						}else {
                							eyymmdd = eyymmdd.substring(0,6) + day;
                						}
                						xbtime = xbtime.substring(3);
                					 }
                					 String eDate = "20" + eyymmdd + " " + xbtime;
                    				 Date etime = format.parse(eDate);
                    				 work.setEndDate(etime);
                				 }else {
                					 work.setEndDate(format.parse(eyymmdd + " " + "18:00"));
                				 }
                				 work.setOaAccount(uid);
                				 if(StringUtils.isNotBlank(name)) {
                					 work.setUserName(name);
                				 }
                				 work.setId(UUID.randomUUID().toString());
                				 System.out.println(i+"-----"+JSON.toJSONString(work));
                			 }
                		 }
                		 i++;
                	}
                }
    		}
    	}
    	
    	@Override
	    public void run() {
	        try {
	        	improt();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            
	        }
		}
    }
    
    public static void deleteFiles(File srcFile) {
        if (srcFile.exists()) {
            //存放文件夹
            LinkedList<File> directories = new LinkedList<>();
            ArrayList<File> directoryList = new ArrayList<>();
            do {
                File[] files = directories.size() > 0 ? directories.removeFirst().listFiles() : new File[]{srcFile};
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            directories.add(f);
                            directoryList.add(f);
                        } else {
                            f.delete();
                        }
                    }
                }
            } while (directories.size() > 0);
            //这时所有非文件夹都删光了，可以直接删文件夹了(注意从后往前遍历)
            for (int i = directoryList.size() - 1; i >= 0; i--) {
                directoryList.get(i).delete();
            }
        }
    }
    
}
