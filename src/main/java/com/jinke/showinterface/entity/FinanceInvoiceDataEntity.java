package com.jinke.showinterface.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 财务发票信息
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_finance_invoice_data")
public class FinanceInvoiceDataEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**人员姓名*/
	private String userName;
	/**发票审核条数*/
	private Integer invoiceSum;
	/**打败员工比率*/
	@Column(name = "ratio", precision = 8, scale = 2)
	private Double ratio;
}
