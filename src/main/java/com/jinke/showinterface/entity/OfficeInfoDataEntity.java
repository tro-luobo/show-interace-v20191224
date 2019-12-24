package com.jinke.showinterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 办公信息
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_office_info_data")
public class OfficeInfoDataEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**上班*/
	@Column(columnDefinition = "DATE")
	private Date startDate;
	/**下班*/
	@Column(columnDefinition = "DATE")
	private Date endDate;
	/**会议参加次or发起次数*/
	private Integer meetingCount;
	/**出勤率*/
	@Column(name = "attendance", precision = 8, scale = 2)
	//@Column(name = "grade", columnDefinition="double(10,2) default '0.00'"
	private Double attendance;
}
