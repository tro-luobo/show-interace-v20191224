package com.jinke.showinterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 审签信息
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_flow_data")
public class FlowDataEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**审批数量*/
	private Integer spSum;
	/**发起数量*/
	private Integer fqSum;
	/**平均耗时*/
	private Double avgTime;
	/**最快审批耗时*/
	private Double startTime;
	/**最慢审批耗时*/
	private Double endTime;
	/**最晚审批时间*/
	@Column(name = "over_date", precision = 8, scale = 2)
	private Date overDate;
	
	//@Transient
	private Double ratio;
}
