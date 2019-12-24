package com.jinke.showinterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

/**
 * 考勤数据
 * */
@Data
@Entity
@Table(name = "jk_check_work")
public class CheckWorkEntity {
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
	
	private String userName;
}
