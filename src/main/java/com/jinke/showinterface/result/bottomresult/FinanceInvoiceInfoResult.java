package com.jinke.showinterface.result.bottomresult;

import java.io.Serializable;

import com.jinke.showinterface.result.bean.BadgeType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 财务发票审核数
 * @author luobo
 * */
@Setter
@Getter
public class FinanceInvoiceInfoResult implements Serializable {
	/**发票审核条数*/
	@ApiModelProperty("发票审核条数")
	private Integer invoiceSum;
	/**打败员工比率*/
	@ApiModelProperty("打败员工比率")
	private String ratio;
	/**发票高度*/
	@ApiModelProperty("发票高度(对比楼层高度)")
	private String hight; 
	@ApiModelProperty("获得徽章")
	private BadgeType badgeType;
}
