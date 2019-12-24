package com.jinke.showinterface.utils.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeCompare implements Serializable{
	@ApiModelProperty("以小时为单位(保留俩位小数)")
    private double hour;
	
	@ApiModelProperty("以分钟为单位(保留俩位小数)")
    private double minute;
	
	@ApiModelProperty("以秒为单位")
    private int second;
}
