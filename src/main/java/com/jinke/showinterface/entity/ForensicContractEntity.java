package com.jinke.showinterface.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 法务合同
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_forensic_contract")
public class ForensicContractEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**平均时效*/
	@Column(name = "avg_time", precision = 8, scale = 2)
	private Double avgTime;
	/**归档时效*/
	@Column(name = "complete_time", precision = 8, scale = 2)
	private Double completeTime;
}
