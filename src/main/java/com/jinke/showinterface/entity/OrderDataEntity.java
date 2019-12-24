package com.jinke.showinterface.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderDataEntity implements Serializable{
	private Integer processNum;
	
	private String oaAccount;
	
	private Integer contractsNum;
	
	private Integer invoiceSum;
	
	private String ratio;
	
	private Integer usageCount;
	
	private Integer handleSum;
	
	private Integer partakeSum;
	
	private String setCm;
	
	private Integer setNum;
}
