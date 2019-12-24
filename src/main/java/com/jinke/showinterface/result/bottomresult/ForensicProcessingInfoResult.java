package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 法务用印信息
 * @author luobo
 * */
@Setter
@Getter
public class ForensicProcessingInfoResult implements Serializable {
	/**用印次数*/
	@ApiModelProperty("用印次数")
	private Integer usageCount;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
