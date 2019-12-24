package com.jinke.showinterface.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 结算信息
 * */
@Data
@Entity
@Table(name = "jk_settlement_info")
public class SettlementInfoEntity {
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**人员姓名*/
	private String userName;
	/**数量*/
	private Integer num;
	/**类型 1：包干，2协议*/
	private String Type;
}
