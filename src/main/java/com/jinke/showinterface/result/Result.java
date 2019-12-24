package com.jinke.showinterface.result;

import java.io.Serializable;

import com.jinke.showinterface.result.bottomresult.AllContractInfoResult;
import com.jinke.showinterface.result.bottomresult.CapitalAllocationInfoResult;
import com.jinke.showinterface.result.bottomresult.EvectionInfoResult;
import com.jinke.showinterface.result.bottomresult.FinanceInvoiceInfoResult;
import com.jinke.showinterface.result.bottomresult.FlowInfoResult;
import com.jinke.showinterface.result.bottomresult.ForensicProcessingInfoResult;
import com.jinke.showinterface.result.bottomresult.InformationInfoResult;
import com.jinke.showinterface.result.bottomresult.OfficeInfoResult;
import com.jinke.showinterface.result.bottomresult.SaleAmountInfoResult;
import com.jinke.showinterface.result.bottomresult.SettlementInfoResult;
import com.jinke.showinterface.result.bottomresult.UserInfoResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口返回数据定义
 * @author luobo
 * */
@Setter
@Getter
public class Result implements Serializable{
	/**员工信息*/
	@ApiModelProperty("员工信息")
	private UserInfoResult userInfo;
	
	/**办公数据*/
	@ApiModelProperty("办公数据")
	private OfficeInfoResult officeInfo;
	
	/**审签信息*/
	@ApiModelProperty("审签信息")
	private FlowInfoResult flowInfo;
	
	/**出差信息*/
	@ApiModelProperty("出差信息")
	private EvectionInfoResult evectionInfo;
	
	/**财务发票审核信息*/
	@ApiModelProperty("财务发票审核信息")
	private FinanceInvoiceInfoResult financeInvoiceInfo;
	
	/**法务用印信息*/
	@ApiModelProperty("法务用印信息")
	private ForensicProcessingInfoResult forensicProcessingInfo;
	
	/**全员合同信息*/
	@ApiModelProperty("全员合同信息")
	private AllContractInfoResult allContractInfo;
	
	/**销售数据*/
	@ApiModelProperty("销售数据")
	private SaleAmountInfoResult saleAmountInfo;
	
	/**信息报事*/
	@ApiModelProperty("信息报事")
	private InformationInfoResult informationInfo;
	
	/**资金调拨*/
	@ApiModelProperty("资金调拨")
	private CapitalAllocationInfoResult capitalAllocationInfo;
	
	/**结算信息*/
	@ApiModelProperty("结算信息")
	private SettlementInfoResult settlementInfo;
	
	@ApiModelProperty("错误代码")
	private String errorCode;
	
	@ApiModelProperty("错误信息")
	private String errorMsg;
	
}
