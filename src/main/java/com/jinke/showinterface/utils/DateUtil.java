package com.jinke.showinterface.utils;

import java.time.LocalDate;
import java.time.Period;
import com.jinke.showinterface.utils.bean.DayCompare;
import com.jinke.showinterface.utils.bean.TimeCompare;

public class DateUtil {
	
	public static DayCompare dayComparePrecise(String fromDate, String toDate){
        Period period = Period.between(LocalDate.parse(fromDate), LocalDate.parse(toDate));
        return DayCompare.builder().day(period.getDays()).month(period.getMonths()).year(period.getYears()).build();
    }
	
	public static TimeCompare timeCompare(Double second) {
		if(second == null) {
			return null;
		}else {
			return TimeCompare.builder().hour(DoubleUtil.div(second, 3600, 2)).minute(DoubleUtil.div(second, 60, 2)).second((int)((double)second)).build();
		}
	}
}
