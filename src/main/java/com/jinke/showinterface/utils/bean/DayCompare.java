package com.jinke.showinterface.utils.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DayCompare implements Serializable{
	@ApiModelProperty("相差年")
    private int year;
	@ApiModelProperty("相差月")
    private int month;
	@ApiModelProperty("相差日")
    private int day;
}
