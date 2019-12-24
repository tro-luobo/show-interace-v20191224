package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 出差信息
 * @author luobo
 * */
@Setter
@Getter
public class EvectionInfoResult implements Serializable {
	/**出差次数*/
	@ApiModelProperty("出差次数")
	private Integer businessTravel;
	/**出差城市数量*/
	@ApiModelProperty("出差城市数量")
	private Integer cityNum;
	/**日期yyyy-MM-dd*/
	@ApiModelProperty("日期yyyy-MM-dd")
	private String time;
	/**天数*/
	@ApiModelProperty("天数")
	private Integer day;
	/**距离*/
	@ApiModelProperty("出差距离抽象(与珠峰高度做对比如：珠峰高度xxxx倍)")
	private String distance;
	/**出差出发地*/
	@ApiModelProperty("出差出发地")
	private String satrtAdd;
	/**出差目的地*/
	@ApiModelProperty("出差目的地")
	private String endAdd;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
