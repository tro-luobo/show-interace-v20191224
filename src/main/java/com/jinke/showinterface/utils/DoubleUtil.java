package com.jinke.showinterface.utils;

import java.math.BigDecimal;

public class DoubleUtil {
	//加法
	public static double add(double v1,double v2){ 
		BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
		return b1.add(b2).doubleValue(); 
	}
	//减法
	public static double subtract(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
		BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
		return b1.subtract(b2).doubleValue();
	}
	//剩法
	public static double mul(double d1, double d2){
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}
	//除法
	public static double div(double d1,double d2,int len) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	// 进行四舍五入操作
	public double round(double d,int len) { 
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, len,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
