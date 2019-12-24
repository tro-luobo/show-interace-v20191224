package com.jinke.showinterface.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jinke.showinterface.dao.AllContractInfoRepository;
import com.jinke.showinterface.dao.CapitalAllocationRepository;
import com.jinke.showinterface.dao.CheckWorkRepository;
import com.jinke.showinterface.dao.EvectionDataRepository;
import com.jinke.showinterface.dao.FinanceInvoiceDataRepository;
import com.jinke.showinterface.dao.FlowDataRepository;
import com.jinke.showinterface.dao.ForensicProcessingInfoRepository;
import com.jinke.showinterface.dao.InformationRepository;
import com.jinke.showinterface.dao.JdbcTemplateDao;
import com.jinke.showinterface.dao.MeetingRepository;
import com.jinke.showinterface.dao.OfficeInfoDataRepository;
import com.jinke.showinterface.dao.SaleAmountRepository;
import com.jinke.showinterface.dao.SettlementInfoRepository;
import com.jinke.showinterface.dao.UserInfoRepository;
import com.jinke.showinterface.entity.CapitalAllocationEntity;
import com.jinke.showinterface.entity.ContractEntity;
import com.jinke.showinterface.entity.FinanceInvoiceDataEntity;
import com.jinke.showinterface.entity.FlowDataEntity;
import com.jinke.showinterface.entity.ForensicProcessingEntity;
import com.jinke.showinterface.entity.InformationEntity;
import com.jinke.showinterface.entity.MeetingEntity;
import com.jinke.showinterface.entity.SaleAmountEntity;
import com.jinke.showinterface.enums.BadgeEnum;
import com.jinke.showinterface.result.Result;
import com.jinke.showinterface.result.bean.BadgeType;
import com.jinke.showinterface.result.bottomresult.AllContractInfoResult;
import com.jinke.showinterface.result.bottomresult.CapitalAllocationInfoResult;
import com.jinke.showinterface.result.bottomresult.EvectionInfoResult;
import com.jinke.showinterface.result.bottomresult.FinanceInvoiceInfoResult;
import com.jinke.showinterface.result.bottomresult.FlowInfoResult;
import com.jinke.showinterface.result.bottomresult.ForensicProcessingInfoResult;
import com.jinke.showinterface.result.bottomresult.InformationInfoResult;
import com.jinke.showinterface.result.bottomresult.OfficeInfoResult;
import com.jinke.showinterface.result.bottomresult.SaleAmountInfoResult;
import com.jinke.showinterface.result.bottomresult.SettlementInfoResult;
import com.jinke.showinterface.result.bottomresult.UserInfoResult;
import com.jinke.showinterface.service.UserInfoService;
import com.jinke.showinterface.utils.BaiDuMapUtils;
import com.jinke.showinterface.utils.CmUtil;
import com.jinke.showinterface.utils.DateUtil;
import com.jinke.showinterface.utils.ThicknessUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService{
	
	@Resource
	UserInfoRepository userInfoDao;
	
	@Resource
	OfficeInfoDataRepository officeInfoDataDao;
	
	@Resource
	FlowDataRepository flowDataDao;
	
	@Resource
	EvectionDataRepository evectionDataDao;
	
	@Resource
	JdbcTemplateDao jdbcTemplateDao;
	
	@Resource
	FinanceInvoiceDataRepository FinanceInvoiceDataDao;
	
	@Resource
	ForensicProcessingInfoRepository forensicProcessingInfoDao;
	
	@Resource
	AllContractInfoRepository allContractInfoDao;
	
	@Resource
	SaleAmountRepository saleAmountDao;
	
	@Resource
	InformationRepository informationDao;
	
	@Resource
	CapitalAllocationRepository capitalAllocationDao;
	
	@Resource
	CheckWorkRepository checkWorkDao;
	
	@Resource
	MeetingRepository meetingDao;
	
	@Resource
	SettlementInfoRepository settlementInfoDao;
	
	@Override
	public Result getReportData(String oaAccount) {
		Result result = new Result();
		try {
			// 员工信息
			result.setUserInfo(getUserInfo(oaAccount));
			// 办公数据
			result.setOfficeInfo(getOfficeInfo(oaAccount));
			// 审签信息
			result.setFlowInfo(getFlowInfo(oaAccount));
			// 出差信息
			result.setEvectionInfo(getEvectionInfo(oaAccount));
			// 财务发票审核信息
			result.setFinanceInvoiceInfo(getFinanceInfo(oaAccount));
			// 法务用印信息
			result.setForensicProcessingInfo(getForensicProcessingInfo(oaAccount));
			// 全员合同信息
			result.setAllContractInfo(getAllContractInfo(oaAccount));
			// 销售数据
			result.setSaleAmountInfo(getSaleAmountInfo(oaAccount));
			// 信息报事
			result.setInformationInfo(getInformationInfo(oaAccount));
			// 资金调拨
			result.setCapitalAllocationInfo(getCapitalAllocationInfo(oaAccount));
			// 结算信息
			result.setSettlementInfo(getSettlementInfo(oaAccount));
		} catch (Exception e) {
			log.error("用户信息数据库查询获取异常{}",e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 员工信息
	 * @throws ParseException 
	 * */
	public UserInfoResult getUserInfo(String oaAccount) throws ParseException {
		UserInfoResult userInfo = new UserInfoResult();
		try {
			Map<String, Object> resultMap = userInfoDao.getUserInfo(oaAccount);
			if(resultMap != null && resultMap.size() > 0) {
				log.info("员工信息数据库查询获取数据：{}", JSON.toJSONString(resultMap));
				SimpleDateFormat sdf =  new SimpleDateFormat( "yyyy-MM-dd" ); 
				//相差年月
				if(resultMap.get("ADDTIME") != null) {
					userInfo.setDivTime(DateUtil.dayComparePrecise(resultMap.get("ADDTIME").toString(), sdf.format(new Date())));
				}
				userInfo.setAddDate(resultMap.get("ADDDATE")==null?null:resultMap.get("ADDDATE").toString());
				userInfo.setClassmateCount(Integer.parseInt(resultMap.get("CLASSMATECOUNT") == null ? "0" : resultMap.get("CLASSMATECOUNT").toString()));
				userInfo.setInJobUserCount(Integer.parseInt(resultMap.get("INJOBUSERCOUNT") == null ? "0" : resultMap.get("INJOBUSERCOUNT").toString()));
				userInfo.setOaAccount(resultMap.get("OAACCOUNT") == null ? "" : resultMap.get("OAACCOUNT").toString());
				userInfo.setSameBirthdayCount(Integer.parseInt(resultMap.get("SAMEBIRTHDAYCOUNT") == null ? "0" : resultMap.get("SAMEBIRTHDAYCOUNT").toString()));
				long userCount = userInfoDao.count();
				long userMonthCount = Integer.parseInt(resultMap.get("INJOBUSERCOUNT") == null ? "0" : resultMap.get("INJOBUSERCOUNT").toString());
				double a = userMonthCount * 1.0;
				double b = userCount * 1.0;
				String value = String.format("%.2f", (float) a / (float) b * 100);
				userInfo.setProportion(value + "%");
				//徽章-根据入职年份进行对比
				String shoolTitle = resultMap.get("schoolRecruitmentTitle") == null?"":resultMap.get("schoolRecruitmentTitle").toString();
				userInfo.setBadgeType(getBadge("USER", userInfo.getDivTime().getYear(), shoolTitle));
			}else {
				userInfo = null;
			}
		} catch (Exception e) {
			log.error("员工信息查询异常，{}", e.getMessage());
			userInfo = null;
		}
		return userInfo;
	}
	
	/**
	 * 办公信息
	 * @throws ParseException 
	 * */
	public OfficeInfoResult getOfficeInfo(String oaAccount) {
		OfficeInfoResult officeInfo = new OfficeInfoResult();
		try {
			//考勤数据
			Map<String, Object> wd = checkWorkDao.getStartData(oaAccount);
			//会议数据
			MeetingEntity meeting = meetingDao.getMeetingByOaAccount(oaAccount);
			if((wd == null || wd.size()<= 0) && meeting == null) {
				return null;
			}
			Map<String, String> max = new HashMap<String, String>();
			if(wd != null && wd.size() > 0) {
				//最早时间
				officeInfo.setMinDate(wd.get("minDate")==null?"":wd.get("minDate").toString());
				officeInfo.setMinTime(wd.get("minTime")==null?"":wd.get("minTime").toString());
				//概率
				String gl = wd.get("GL")==null?null:wd.get("GL").toString()+"%";
				officeInfo.setAttendance(gl);
				//最晚时间
				max = checkWorkDao.getMaxDate(oaAccount);
				officeInfo.setMaxDate(max.get("MAXDATE"));
				officeInfo.setMaxTime(max.get("MAXTIME"));
				officeInfo.setSbDate(max.get("sbDate")==null?"":max.get("sbDate").toString());
				String type = max.get("TYPE");
				if(org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
					if("1".equals(type)) {
						officeInfo.setType("1");
						officeInfo.setTypeMessage("次日");
					}else {
						officeInfo.setType("0");
						officeInfo.setTypeMessage("当日");
					}
				}
			}
			
			//徽章获取规则
			BadgeType bg = null;
			if(meeting != null) {
				officeInfo.setMeetingCount(meeting.getMeetingCount());
				bg = getBadge("BGHY", meeting.getMeetingCount(), "");
			}
			
			if(bg == null && max.size() > 1) {
				if(org.apache.commons.lang3.StringUtils.isNotBlank(max.get("maxHHTime"))) {
					bg = getBadge("BGXB", Integer.parseInt(max.get("maxHHTime")) , "");
				}
			}
			
			if(bg == null) {
				bg = getBadge("BGSB", meeting.getMeetingCount(), "");
			}
			officeInfo.setBadgeType(bg);
		} catch (Exception e) {
			log.error("办公信息查询异常，{}", e.getMessage());
			officeInfo = null;
		}
		return officeInfo;
	}
	
	/**
	 * 审签信息
	 * */
	public FlowInfoResult getFlowInfo(String oaAccount) {
		FlowInfoResult flowInfo = new FlowInfoResult();
		FlowDataEntity flowData = flowDataDao.getFlowDataByOaAccount(oaAccount);
		log.info("审签数据库查询获取数据：{}", JSON.toJSONString(flowData));
		if(flowData != null) {
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
			flowInfo.setAvgTime(DateUtil.timeCompare(flowData.getAvgTime()));
			flowInfo.setStartTime(DateUtil.timeCompare(flowData.getStartTime()));
			flowInfo.setRatio(flowData.getRatio() + "%");
			flowInfo.setEndTime(DateUtil.timeCompare(flowData.getEndTime()));
			int hh = 0;
			if(flowData.getOverDate() != null) {
				sdf = new SimpleDateFormat("yyyy年MM月dd日");
				flowInfo.setLatestDate(sdf.format(flowData.getOverDate()));
				sdf = new SimpleDateFormat("HH:mm");
				flowInfo.setLatestTime(sdf.format(flowData.getOverDate()));
				sdf = new SimpleDateFormat("HH");
				hh = Integer.parseInt(sdf.format(flowData.getOverDate()));
			}
			flowInfo.setFlowFqNum(flowData.getFqSum());
			flowInfo.setFlowSpNum(flowData.getSpSum());
			
			//徽章-根据审批条数
			BadgeType bg = getBadge("SQTS", flowData.getSpSum(), "");
			if(bg == null) {
				//根据审核时间
				bg = getBadge("SQSJ", flowData.getStartTime().intValue(), "");
			}
			if(bg == null) {
				bg = getBadge("SQXB", hh , "次日")	;
			}
			flowInfo.setBadgeType(bg);
			//根据最晚下班时间
			flowInfo.setBadgeType(bg);
		}else {
			flowInfo = null;
		}
		return flowInfo;
	}
	
	/**
	 * 出差数据
	 * */
	public EvectionInfoResult getEvectionInfo(String oaAccount) {
		EvectionInfoResult evectionInfo = new EvectionInfoResult();
		try {
			int eceCount = evectionDataDao.getEvectionCount(oaAccount);
			if(eceCount <= 0) {
				return null;
			}
			log.info("出差数据-出差次数：{}", eceCount);
			int cityCount = evectionDataDao.getCityCount(oaAccount);
			log.info("出差数据-到过城市数：{}", eceCount);
			Map<String, Object> evecData = evectionDataDao.getInfoMap(oaAccount);
			evectionInfo.setBusinessTravel(eceCount);
			evectionInfo.setCityNum(cityCount);
			if(evecData != null) {
				log.info("出差数据-出差信息统计：{}", evecData.toString());
				if(!StringUtils.isEmpty(evecData.get("DAY"))) {
					evectionInfo.setDay(Integer.parseInt(evecData.get("DAY").toString()));
				}
				String startAdd = evecData.get("START_ADD") == null ? "" : evecData.get("START_ADD").toString();
				String endAdd = evecData.get("END_ADD") == null ? "" : evecData.get("END_ADD").toString();
				evectionInfo.setSatrtAdd(startAdd);
				evectionInfo.setEndAdd(endAdd);
				evectionInfo.setTime(evecData.get("START_TIME") == null ? "" : evecData.get("START_TIME").toString());
				//总里程数
				double distanceSum = evectionDataDao.getDistanceSum(oaAccount);
				evectionInfo.setDistance(BaiDuMapUtils.getDistance(distanceSum));
				evectionInfo.setBadgeType(getBadge("CC", 0, ""));
			}
		} catch (Exception e) {
			log.error("出差信息查询异常，{}", e.getMessage());
			evectionInfo = null;
		}
		return evectionInfo;
	}
	
	/**
	 * 财务发票审核信息
	 * */
	public FinanceInvoiceInfoResult getFinanceInfo(String oaAccount) {
		FinanceInvoiceDataEntity financeInvoiceData = FinanceInvoiceDataDao.getFinanceInvoiceDataByOaAccount(oaAccount);
		if(financeInvoiceData == null) {
			return null;
		}
		FinanceInvoiceInfoResult result = new FinanceInvoiceInfoResult();
		result.setInvoiceSum(financeInvoiceData.getInvoiceSum());
		//计算审核发票数占全公司打败占比
		result.setRatio(FinanceInvoiceDataDao.getRatio(oaAccount) + "%");
		//发票高度抽象实现
		result.setHight(ThicknessUtil.getAbstractCount(result.getInvoiceSum()));
		result.setBadgeType(getBadge("FP", 0, ""));
		return result;
	}
	
	/**
	 * 法务用印信息
	 * */
	public ForensicProcessingInfoResult getForensicProcessingInfo(String oaAccount) {
		ForensicProcessingEntity forensicProcessing = forensicProcessingInfoDao.getForensicProcessingByOaAccount(oaAccount);
		if(forensicProcessing == null) {
			return null;
		}
		ForensicProcessingInfoResult result = new ForensicProcessingInfoResult();
		result.setUsageCount(forensicProcessing.getUsageCount());
		result.setBadgeType(getBadge("FPYY", 0, ""));
		return result;
	}
	
	/**
	 * 全员合同信息
	 * */
	public AllContractInfoResult getAllContractInfo(String oaAccount) {
		ContractEntity contract = allContractInfoDao.getContractByOaAccount(oaAccount);
		if(contract == null) {
			return null;
		}
		AllContractInfoResult result = new AllContractInfoResult();
		result.setContractsNum(contract.getContractsNum());
		result.setBadgeType(getBadge("FPHT", 0, ""));
		return result;
	}
	
	/**
	 * 销售数据
	 * */
	public SaleAmountInfoResult getSaleAmountInfo(String oaAccount) {
		SaleAmountEntity saleAmount = saleAmountDao.getSaleAmountByOaAccount(oaAccount);
		if(saleAmount ==null) {
			return null;
		}
		SaleAmountInfoResult result = new SaleAmountInfoResult();
		result.setSetNum(saleAmount.getSetNum());
		result.setSetCm(saleAmount.getSetCm() + "");
		result.setLargerThanSize(CmUtil.getMultiple(Double.valueOf(saleAmount.getSetCm())));
		result.setBadgeType(getBadge("XS", 0, ""));
		return result;
	}
	
	/**
	 * 信息报事
	 * */
	public InformationInfoResult getInformationInfo(String oaAccount) {
		InformationEntity information = informationDao.getInformationByOaAccount(oaAccount);
		if(information ==null) {
			return null;
		}
		InformationInfoResult result = new InformationInfoResult();
		result.setHandleSum(information.getHandleSum());
		result.setPartakeSum(information.getPartakeSum());
		result.setBadgeType(getBadge("XX", 0, ""));
		return result;
	}
	
	/**
	 * 资金调拨
	 * */
	public CapitalAllocationInfoResult getCapitalAllocationInfo(String oaAccount) {
		CapitalAllocationEntity capitalAllocation = capitalAllocationDao.getCapitalAllocationByOaAccount(oaAccount);
		if(capitalAllocation ==null) {
			return null;
		}
		CapitalAllocationInfoResult result = new CapitalAllocationInfoResult();
		result.setProcessNum(capitalAllocation.getProcessNum());
		result.setBadgeType(getBadge("ZJ", 0, ""));
		return result;
	}
	
	/**
	 * 结算信息
	 * */
	public SettlementInfoResult getSettlementInfo(String oaAccount) {
		//查询包干数
		int zs = settlementInfoDao.getSum(oaAccount, "1");
		//查询协议数
		int xys = settlementInfoDao.getSum(oaAccount, "2");
		if(zs == 0 && xys == 0) {
			return null;
		}
		SettlementInfoResult result = new SettlementInfoResult();
		result.setTotalPriceNum(zs);
		result.setSettlementAgreementNum(xys);
		result.setBadgeType(getBadge("JS", 0, ""));
		return result;
	}
	
	/**
	 * 徽章获得规则
	 * */
	public static BadgeType getBadge(String type, int bj, String bjs) {
		BadgeType result = null;
		List<BadgeEnum> vs = BadgeEnum.getBadegeList(type);
		for(BadgeEnum be:vs) {
			// 区间比较
			if("SECTION".equals(be.getType())) {
				if(bj >= be.getLarge() && bj < be.getSmall()) {
					result = new BadgeType();
					result.setCode(be.name());
					result.setValue(be.getMessage());
					break;
				}
			}else if("EQUALS".equals(be.getType())) {// 值比较
				if(bjs.equals(be.getEq())) {
					result = new BadgeType();
					result.setCode(be.name());
					result.setValue(be.getMessage());
					break;
				}
			}else if("EQUALSANDSECTION".equals(be.getType())) {// 值比较并且区间范围
				if(bjs.equals(be.getEq()) && bj >= be.getLarge() && bj < be.getSmall() ) {
					result = new BadgeType();
					result.setCode(be.name());
					result.setValue(be.getMessage());
					break;
				}
			}else if("EQUALSORSECTION".equals(be.getType())) {// 值比较并且区间范围
				if(bjs.equals(be.getEq()) || (bj >= be.getLarge() && bj < be.getSmall())) {
					result = new BadgeType();
					result.setCode(be.name());
					result.setValue(be.getMessage());
					break;
				}
			}else if("DEF".equals(be.getType())){
				result = new BadgeType();
				result.setCode(be.name());
				result.setValue(be.getMessage());
				break;
			}
		}
		return result;
	}
	
}
