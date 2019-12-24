package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 信息事报
 * @author luobo
 * */
@Setter
@Getter
public class InformationInfoResult implements Serializable {
	/**参与报事处理的系统数*/
	@ApiModelProperty("参与报事处理的系统数")
	private Integer partakeSum;
	/**处理次数*/
	@ApiModelProperty("处理次数")
	private Integer handleSum;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
