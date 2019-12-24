package com.jinke.showinterface.enums;

/**
 * 描述地址理解程度。分值范围0-100，分值越大，服务对地址理解程度越高
 * @author luobo
 * */
public enum ComprehensionEnum {
	
	C_100(100, "解析误差100m内概率为91%，误差500m内概率为96%"),
	C_90(90, "解析误差100m内概率为89%，误差500m内概率为96%"),
	C_80(80, "解析误差100m内概率为88%，误差500m内概率为95%"),
	C_70(70, "解析误差100m内概率为84%，误差500m内概率为93%"),
	C_60(60, "解析误差100m内概率为81%，误差500m内概率为91%"),
	C_50(50, "解析误差100m内概率为79%，误差500m内概率为90%");
	
	ComprehensionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ComprehensionEnum byCode(int code) {
        for (ComprehensionEnum enums : ComprehensionEnum.values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
