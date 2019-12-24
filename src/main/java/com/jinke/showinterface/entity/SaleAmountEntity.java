package com.jinke.showinterface.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 销售数据
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_sale_amount")
public class SaleAmountEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**销售套数*/
	private Integer setNum;
	/**销售面积*/
	@Column(name = "set_cm", precision = 8, scale = 2)
	private Double setCm;
}
