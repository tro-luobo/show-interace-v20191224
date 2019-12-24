package com.jinke.showinterface.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 信息报事-IT客服
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_information")
public class InformationEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**参与报事处理的系统数*/
	private Integer partakeSum;
	/**处理次数*/
	private Integer handleSum;
}
