package com.jinke.showinterface.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
@Table(name = "jk_meeting")
public class MeetingEntity {
	@Id
	@GenericGenerator(name = "ID", strategy = "uuid")
	private String id;
	/**oa账户*/
	private String oaAccount;
	/**人员编码，主数据编码*/
	private String userCode;
	/**会议参加次or发起次数*/
	private Integer meetingCount;
}
