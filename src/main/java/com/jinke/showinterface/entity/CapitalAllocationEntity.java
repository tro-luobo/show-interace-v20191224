package com.jinke.showinterface.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 资金调拨单数-ERP
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_capital_allocation")
public class CapitalAllocationEntity {
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
	/**调拨条数*/
	private Integer processNum;
}
