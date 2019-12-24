package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;
import com.jinke.showinterface.utils.bean.TimeCompare;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 审签数据
 * @author luobo
 * */
@Setter
@Getter
public class FlowInfoResult implements Serializable {
	/**平均耗时*/
	@ApiModelProperty("平均耗时")
	private TimeCompare avgTime;
	/**最慢审批耗时*/
	@ApiModelProperty("最慢审批耗时")
	private TimeCompare endTime;
	/**最快审批耗时*/
	@ApiModelProperty("最快审批耗时")
	private TimeCompare startTime;
	/**击败人比率*/
	@ApiModelProperty("击败人比率")
	private String ratio;
	@ApiModelProperty("最晚审核时间hh:mm")
	private String latestTime;
	@ApiModelProperty("最晚审核日期yyyy年xx月xx日")
	private String latestDate;
	@ApiModelProperty("流程审批数量")
	private Integer flowSpNum;
	@ApiModelProperty("流程发起数量")
	private Integer flowFqNum;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
