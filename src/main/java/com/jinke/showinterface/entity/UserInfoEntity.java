package com.jinke.showinterface.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 员工信息
 * @author luobo
 * */
@Data
@Entity
@Table(name = "jk_user_info")
public class UserInfoEntity {
	/**uuid*/
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**加入金科日期*/
	@Column(columnDefinition = "DATE")
	private Date addDate;
	/**出生日期*/
	@Column(columnDefinition = "DATE")
	private Date birthDate;
	/**毕业学校*/
	private String graduateSchool;
	/**是否校招生*/
	private String isSchoolRecruitment;
	/**校招生品牌*/
	private String schoolRecruitmentTitle;
	/**人员编码，主数据编码*/
	private String userCode;
}
