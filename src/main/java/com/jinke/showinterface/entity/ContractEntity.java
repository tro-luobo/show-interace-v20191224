package com.jinke.showinterface.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 合同全员发起次数
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_contract")
public class ContractEntity {
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
	/**所属公司*/
	private String company;
	/**合同发起次数*/
	private Integer contractsNum;
}
