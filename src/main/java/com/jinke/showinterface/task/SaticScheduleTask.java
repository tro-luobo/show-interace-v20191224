package com.jinke.showinterface.task;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.jinke.showinterface.controlller.ImportController;
import com.jinke.showinterface.dao.CheckWorkRepository;
import com.jinke.showinterface.dao.EvectionDataRepository;
import com.jinke.showinterface.dao.UserInfoRepository;
import com.jinke.showinterface.entity.CheckWorkEntity;
import com.jinke.showinterface.entity.EvectionDataEntity;
import com.jinke.showinterface.result.Result;
import com.jinke.showinterface.service.UserInfoService;
import com.jinke.showinterface.utils.BaiDuMapUtils;
import com.jinke.showinterface.utils.DingDing;
import com.jinke.showinterface.utils.ExcelUtil;
import com.jinke.showinterface.utils.PingIpUtil;
import com.jinke.showinterface.utils.RedisUtils;
import com.jinke.showinterface.utils.bean.ExcelData;
import com.jinke.showinterface.utils.bean.ExcelLineData;
import com.jinke.showinterface.utils.bean.ExcelSheetData;
import lombok.extern.slf4j.Slf4j;

@Configuration      
@EnableScheduling  
@Slf4j
@Component
public class SaticScheduleTask {
	
	@Autowired
	private RedisUtils redisUtils;
	    
	@Autowired
	private UserInfoRepository userDao;
	    
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	EvectionDataRepository EevectionDataDao;
	
	@Autowired
	CheckWorkRepository checkWorkDao;
	
	@Scheduled(cron = "0 0 8 31 12 ?")
    private void configureTasks() throws Exception {
    	log.info("执行钉钉消息推送-开始");
    	DingDing.sendWorkMessage("");
    	log.info("执行钉钉消息推送-结束");
    	log.info("执行静态定时任务时间: " + LocalDateTime.now());
    }

	/**
	 * 缓存数据定时更新存入
	 * */
    @Scheduled(cron = "0 10 18 * * ?")
    private void chceData() {
    	log.info("定时任务--缓存数据更新开始");
    	try {
			//清除redis所有缓存数据
    		List<String> list = userDao.findByOaAccountList();
    		for(String oaAccount : list) {
    			redisUtils.remove(oaAccount);
    			Result result = userInfoService.getReportData(oaAccount);
        		redisUtils.set(oaAccount, result);
        		log.info("定时任务存入缓存数据：{}", oaAccount);
    		}
		} catch (Exception e) {
			log.error("缓存数据更新失败{}", e.getMessage());
		}
    }
    
    /**
     * 计算百度里程数据
     * */
    @Scheduled(cron = "0 0 23 * * ?")
    private void improtSumBaiduLc() throws Exception {
    	log.info("定时任务-百度里程计算开始");
    	//判断网络是否畅通
    	Sort sort = new Sort(Sort.Direction.DESC,"id"); //创建时间降序排序
    	Pageable pageable = null;
    	int len = EevectionDataDao.findAllCountTest();
    	int pageSize = 50;
    	int count = len%pageSize==0?(len/pageSize):(len/pageSize)+1;
    	for(int i=0;i <= count;i++){
    		pageable = new PageRequest(i,pageSize,sort);
    		Page<EvectionDataEntity> list = EevectionDataDao.findAllPageTest(pageable);
    		for(EvectionDataEntity e : list) {
    			try {
    				e.setDistance(BaiDuMapUtils.getDistanceDouble(e.getStartAdd(), e.getEndAdd()));
    			    log.info("更新数据：{}",e.getId()+"("+e.getUserName()+")");
    			} catch (Exception e2) {
    				e2.printStackTrace();
    				e.setDistance(0.0);
    				log.info("更新数据异常：{}",e.getId()+"("+e.getUserName()+")异常信息："+e2.getMessage());
    			}
    			EevectionDataDao.save(e);
    		}
    	}
    	log.info("定时任务-百度里程计算完毕,已计算数据:" + count);
    }
    
    /**
     * 考勤数据导入
     * */
    @Scheduled(cron = "0 0 20 * * ?")
    private void importExcel() throws Exception {
    	log.info("定时任务-考勤数据导入开始");
    	try {
			String oldPath = "/home/userapp/dataform";
			String ydPath = "/home/userapp/datato";
	    	File file = new File(oldPath);
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            for (File file2 : files) {
	                if (!file2.isDirectory()) {
	                	log.info("原始文件:" + file2.getName());
	                	importUtil(file2);
	                	log.info("成功导入文件:" + file2.getName());
	                	String UUIDStr = UUID.randomUUID().toString();
	                	log.info("导入成功后移动至:" + ydPath + "/" + UUIDStr + ".xlsx" );
	                	file2.renameTo(new File(ydPath + "/" + UUIDStr + ".xlsx" ));
	                }
	            }
	        }
	        log.error("定时任务-考勤数据导入完毕");
		} catch (Exception e) {
			log.error("定时任务-考勤数据导入出错:{}",e.getMessage());
		}
    }
    
    public void importUtil(File file) throws ParseException {
		ExcelUtil excelReader = new ExcelUtil();
            ExcelData excelData = excelReader.readMultiSheetExcel(file);
            for(ExcelSheetData esd : excelData.getSheetData()) {
            	List<ExcelLineData> list = esd.getLineData();
            	int i = 0;
            	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            	for(ExcelLineData el : list) {
            		try {
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
               				 log.info(rq);
               				 String syymmdd = rq.substring(0, 8);
               				 String sDate = "20"+syymmdd + " " + sbtime;
               				 log.info(sDate);
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
               					 work.setEndDate(format.parse("20" + eyymmdd + " " + "18:00"));
               				 }
               				 work.setOaAccount(uid);
               				 if(StringUtils.isNotBlank(name)) {
               					 work.setUserName(name);
               				 }
               				 work.setId(UUID.randomUUID().toString());
               				 checkWorkDao.save(work);
               			 }
               		 }
					} catch (Exception e) {
						log.error("错误信息++++++++"+e.getMessage() + JSON.toJSONString(el));
					}
            		i++;
            	}
            }
    }
}
