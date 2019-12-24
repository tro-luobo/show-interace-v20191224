package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;
import java.util.Date;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 办公数据信息
 * @author luobo
 * */
@Setter
@Getter
public class OfficeInfoResult implements Serializable {
	/**最早上班*/
	@ApiModelProperty("最早上班日期yyyy-MM-dd")
	private String minDate;
	@ApiModelProperty("最早上班时间HH:MM")
	private String minTime;
	/**最晚下班*/
	@ApiModelProperty("最晚下班日期yyyy-MM-dd")
	private String maxDate;
	@ApiModelProperty("最晚下班时间HH:MM")
	private String maxTime;
	/**会议参加or发起*/
	@ApiModelProperty("会议参加or发起次数")
	private Integer meetingCount;
	/**超过比率*/
	@ApiModelProperty("超过比率")
	private String attendance;
	@ApiModelProperty("下班时间如果为次日则为1，否则为0")
	private String type;
	@ApiModelProperty("下班时间次日，当日")
	private String typeMessage;
	@ApiModelProperty("最晚下班日的上班日期")
	private String sbDate;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
