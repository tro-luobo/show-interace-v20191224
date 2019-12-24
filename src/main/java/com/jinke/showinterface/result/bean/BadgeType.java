package com.jinke.showinterface.result.bean;

import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BadgeType implements Serializable{
	@ApiModelProperty("徽章编码")
	private String code;
	@ApiModelProperty("徽章含义")
	private String value;
}
