package com.jinke.showinterface.utils;

public class CmUtil {
	
	//足球场面积单位平方米
	final static double MAIN_STADIUM = 7140.00;
	
	public static String getMultiple(double cm) {
		double multiple = DoubleUtil.div(cm, MAIN_STADIUM, 2);
		return DicimalToFractionUtil.dicimalToFraction(multiple);
	}
	
	public static void main(String[] args) {
		double multiple = DoubleUtil.div(8394.71, MAIN_STADIUM, 2);
		System.out.println(DicimalToFractionUtil.dicimalToFraction(multiple));
	}
}
