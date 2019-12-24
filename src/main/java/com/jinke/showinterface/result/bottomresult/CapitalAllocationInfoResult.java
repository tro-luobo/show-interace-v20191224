package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 资金调拨
 * @author luobo
 * */
@Setter
@Getter
public class CapitalAllocationInfoResult implements Serializable {
	/**调拨条数*/
	@ApiModelProperty("调拨条数")
	private Integer processNum;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
