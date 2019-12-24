package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 全员合同信息
 * @author luobo
 * */
@Setter
@Getter
public class AllContractInfoResult implements Serializable {
	/**合同发起次数*/
	@ApiModelProperty("合同发起次数")
	private Integer contractsNum;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
