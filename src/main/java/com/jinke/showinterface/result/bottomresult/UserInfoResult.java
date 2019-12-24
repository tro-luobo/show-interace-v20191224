package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;
import com.jinke.showinterface.enums.BadgeEnum;
import com.jinke.showinterface.result.bean.BadgeType;
import com.jinke.showinterface.utils.bean.DayCompare;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 人员信息
 * @author luobo
 * */
@Setter
@Getter
public class UserInfoResult implements Serializable{
	@ApiModelProperty("oa账号")
	private String oaAccount;
	
	@ApiModelProperty("入职相差年月日")
	private DayCompare divTime;
	
	@ApiModelProperty("入职日期")
	private String addDate;

	@ApiModelProperty("同年同月入职数")
	private int inJobUserCount;
	
	@ApiModelProperty("同期入职占全公司总人数占比")
	private String proportion;
	
	@ApiModelProperty("同生日")
	private int sameBirthdayCount;
	
	@ApiModelProperty("同校")
	private int classmateCount; 
	
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
	
}
