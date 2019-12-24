package com.jinke.showinterface.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 出差数据
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_evection_data")
public class EvectionDataEntity {
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
	/**出发地*/
	private String startAdd;
	/**目的地*/
	private String endAdd;
	/**开始时间*/
	@Column(columnDefinition = "DATE")
	private Date startTime;
	/**结束时间*/
	@Column(columnDefinition = "DATE")
	private Date endTime;
	/**距离*/
	@Column(name = "distance", precision = 8, scale = 2)
	private Double distance;
}
