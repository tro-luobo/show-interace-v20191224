package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 销售销售数据
 * @author luobo
 * */
@Setter
@Getter
public class SaleAmountInfoResult implements Serializable {
	/**销售套数*/
	@ApiModelProperty("销售套数*")
	private Integer setNum;
	/**销售面积*/
	@ApiModelProperty("销售面积")
	private String setCm;
	/**实物参照*/
	@ApiModelProperty("与足球场实物参照(多少个足球场大小)")
	private String largerThanSize;
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
