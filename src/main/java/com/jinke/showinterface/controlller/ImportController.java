package com.jinke.showinterface.controlller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jinke.showinterface.entity.UserInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.jinke.showinterface.dao.CheckWorkRepository;
import com.jinke.showinterface.dao.EvectionDataRepository;
import com.jinke.showinterface.entity.CheckWorkEntity;
import com.jinke.showinterface.entity.EvectionDataEntity;
import com.jinke.showinterface.result.Result;
import com.jinke.showinterface.service.UserInfoService;
import com.jinke.showinterface.utils.BaiDuMapUtils;
import com.jinke.showinterface.utils.ExcelUtil;
import com.jinke.showinterface.utils.PingIpUtil;
import com.jinke.showinterface.utils.RedisUtils;
import com.jinke.showinterface.utils.bean.ExcelData;
import com.jinke.showinterface.utils.bean.ExcelLineData;
import com.jinke.showinterface.utils.bean.ExcelSheetData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于数据初始化导入
 * @author luobo
 * */
@RestController
@Slf4j
@Api(value = "用于数据初始化导入")
public class ImportController {
	
	public final static String URL = "http://api.map.baidu.com";
	
	@Autowired
	EvectionDataRepository EevectionDataDao;
	
	@Autowired
	CheckWorkRepository checkWorkDao;
	
	@Autowired
	RedisUtils redis;
	
	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping(value = "/getRedisKey", method = RequestMethod.GET)
	@ApiOperation(value = "根据key获取redis缓存", notes = "根据key获取redis缓存")
	public String getRedisByKey(@ApiParam(name = "key", value = "缓存key", required = true) @RequestParam String key) {
		log.info("获取key：{}", key);
		Object obj = redis.get(key);
		if(obj != null) {
			return JSON.toJSONString(obj);
		}
		return null;
	}
	
	@RequestMapping(value = "/getDataKey", method = RequestMethod.GET)
	@ApiOperation(value = "根据Oa账号获取数据库信息", notes = "根据Oa账号获取数据库信息")
	public Result getDataByKey(@ApiParam(name = "oaAccount", value = "Oa账号", required = true) @RequestParam String oaAccount) {
		log.info("Oa账号：{}", oaAccount);
		Result result = userInfoService.getReportData(oaAccount);
		if(result != null) {
			return result;
		}
		return null;
	}
	
	@RequestMapping(value = "/delRedisKey", method = RequestMethod.GET)
	@ApiOperation(value = "根据key删除redis缓存", notes = "根据key删除redis缓存")
	public String delRedisByKey(@ApiParam(name = "key", value = "缓存key", required = true) @RequestParam String key) {
		log.info("删除key：{}", key);
		redis.remove(key);
		return "true";
	}
	
	@RequestMapping(value = "/importExcelOne", method = RequestMethod.GET)
	@ApiOperation(value = "单线程考勤数据初始化", notes = "单线程考勤数据初始化")
	public void importExcelOne() throws ParseException {
		try {
			String oldPath = "/home/userapp/dataform";
			String ydPath = "/home/userapp/datato";
	    	File file = new File(oldPath);
	    	//清空临时数据
	    	//deleteFiles(new File(newdPath));
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            for (File file2 : files) {
	                if (!file2.isDirectory()) {
	                	log.info("原始文件:" + file2.getName());
	                	importUtil(file2);
	                	log.info("成功导入文件:" + file2.getName());
	                	String UUIDStr = UUID.randomUUID().toString();
	                	log.info("导入成功后移动至:" + ydPath + "\\" + UUIDStr + ".xlsx" );
	                	file2.renameTo(new File(ydPath + "\\" + UUIDStr + ".xlsx" ));
	                }
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**计算出差距离并写入数据表
	 * @throws Exception */
	@RequestMapping(value = "/import1", method = RequestMethod.GET)
	@ApiOperation(value = "单线程里程计算数据初始化", notes = "单线程里程计算数据初始化")
	public String import1(@ApiParam(name = "password", value = "执行密码", required = true) @RequestParam String password) throws Exception {
		//判断网络是否畅通
		/*if(!PingIpUtil.pingIp(URL)) {
			return "服务器无法访问百度地图API，请检查服务器网络环境配置";
		}*/
		if(!"123456".equals(password)) {
			return "执行密码错误";
		}
		Sort sort = new Sort(Sort.Direction.DESC,"id"); //创建时间降序排序
		Pageable pageable = null;
		int len = EevectionDataDao.findAllCountTest();
		int pageSize = 50;
		int count = len%pageSize==0?(len/pageSize):(len/pageSize)+1;
		for(int i=0;i <= count;i++){
			pageable = new PageRequest(i,pageSize,sort);
			Page<EvectionDataEntity> list = EevectionDataDao.findAllPageTest(pageable);
			for(EvectionDataEntity e : list) {
				update(e);
			}
		}
		return "true";
	}
	
	@RequestMapping(value = "/improtThreadMore", method = RequestMethod.GET)
	@ApiOperation(value = "多线程里程计算数据初始化", notes = "多线程里程计算数据初始化")
	public String improtThreadMore(@ApiParam(name = "password", value = "执行密码", required = true) @RequestParam String password) throws Exception {
		//判断网络是否畅通
		/*if(!PingIpUtil.pingIp(URL)) {
			return "服务器无法访问百度地图API，请检查服务器网络环境配置";
		}*/
		if(!"123456".equals(password)) {
			return "执行密码错误";
		}
		int len = EevectionDataDao.findAllCountTest();
		//申名线程数计算
		int oneThreadCount = 2000;
		int threadCount = len%oneThreadCount==0?(len/oneThreadCount):(len/oneThreadCount)+1;
		CountDownLatch countDownLatch = new CountDownLatch(threadCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadCount); 
		try {
			for(int i = 0; i < threadCount; i++) {
				ThreadTask task = new ThreadTask(oneThreadCount, len, i, countDownLatch, EevectionDataDao);
				executor.execute(task);
				log.info("线程"+i+"已启动："+ task.currentThread().getName());
			}
			countDownLatch.await();
			log.info("数据操作完成!可以在此开始其它业务");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			executor.shutdown();
		}
		return "true";
	}
	
	public void update(EvectionDataEntity e) {
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
	
	//多线程数据初始化------
	class ThreadTask extends Thread{
		
		private EvectionDataRepository EevectionDataDao;
		private int oneThreadCount;
		private int count;
		private int i;
		
		private CountDownLatch countDownLatch;
		
		ThreadTask(int oneThreadCount,int count,int i, CountDownLatch countDownLatch, EvectionDataRepository EevectionDataDao){
			this.count = count;
			this.oneThreadCount = oneThreadCount;
			this.i = i;
			this.countDownLatch = countDownLatch;
			this.EevectionDataDao = EevectionDataDao;
		}
		
		public void select(int min,int max) {
			Sort sort = new Sort(Sort.Direction.DESC,"id"); //创建时间降序排序
			Pageable pageable = null;
			pageable = new PageRequest(min,max,sort);
			Page<EvectionDataEntity> list = EevectionDataDao.findAllPageTest(pageable);
			for(EvectionDataEntity e : list) {
				update(e);
			}
		}
		
		public void update(EvectionDataEntity e) {
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
		
		@Override
	    public void run() {
	        try {
	        	select(i,oneThreadCount);
	        	countDownLatch.countDown();
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
               				 //log.info(i+"-----"+JSON.toJSONString(work));
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
