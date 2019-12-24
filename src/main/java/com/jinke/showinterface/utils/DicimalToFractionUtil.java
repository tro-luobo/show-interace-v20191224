package com.jinke.showinterface.utils;

public class DicimalToFractionUtil {

    public static String dicimalToFraction(double num){
    	if(num > 1) {
    		//保留小数
    		//return String.format("%.1f",num);
    		//四舍五入阿拉伯数字
    		return Math.round(num) + "";
    		//四舍五入中文
    		//return ChineseNumToArabicNumUtil.arabicNumToChineseNum((int)Math.round(num)) + "";
    	}
        int count = 0;
        int base = 10;
        while(num != Math.floor(num)){
            num *= base;
            count++;
        }

        base = (int)Math.pow(base,count);
        int nor = (int)num;
        int gcd = findGCD(nor, base);
        return String.valueOf(nor/gcd) + "/" + String.valueOf(base/gcd);
    }
    
    public static String dicimalToFractionALB(double num){
    	if(num > 1) {
    		return Math.round(num) + "";
    	}
        int count = 0;
        int base = 10;
        while(num != Math.floor(num)){
            num *= base;
            count++;
        }

        base = (int)Math.pow(base,count);
        int nor = (int)num;
        int gcd = findGCD(nor, base);
        return String.valueOf(nor/gcd) + "/" + String.valueOf(base/gcd);
    }


    //求最大公约数
    private static int findGCD(int a, int b){
        if(a == 0){
            return b;
        }
        return findGCD(b%a, a);
    }
}
