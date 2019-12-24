package com.jinke.showinterface.utils;

public class ThicknessUtil {
	// 楼高度单位毫米
	final static double ONE_FLOOR_HIGHT = 3000.0;
	// 纸厚度单位毫秒
	final static double ONE_PAPER_HIGHT = 0.4;
	
	public static String getAbstractCount(int count) {
		double paperCountHg = DoubleUtil.mul((double) count, ONE_PAPER_HIGHT);
		double value = DoubleUtil.div(paperCountHg, ONE_FLOOR_HIGHT, 2);
		String result = DicimalToFractionUtil.dicimalToFraction(value);
		return result;
	}
}
