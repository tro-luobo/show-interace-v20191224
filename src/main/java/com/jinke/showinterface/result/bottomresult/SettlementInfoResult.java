package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 结算信息
 * @author luobo
 * */
@Setter
@Getter
public class SettlementInfoResult implements Serializable {
	@ApiModelProperty("总价包干数")
	private Integer totalPriceNum;
	
	@ApiModelProperty("结算协议数量")
	private Integer settlementAgreementNum;
	
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
