package com.jinke.showinterface.enums;

/**
 * 坐标点误差范围 
 * @author luobo
 * */
public enum ConfidenceEnum {
	C_100(100, "坐标点的误差范围：解析误差绝对精度小于20m"),
	C_90(90, "坐标点的误差范围：解析误差绝对精度小于50m"),
	C_80(80, "坐标点的误差范围：解析误差绝对精度小于100m"),
	C_75(75, "坐标点的误差范围：解析误差绝对精度小于200m"),
	C_70(70, "坐标点的误差范围：解析误差绝对精度小于300m"),
	C_60(60, "坐标点的误差范围：解析误差绝对精度小于500m"),
	C_50(50, "坐标点的误差范围：解析误差绝对精度小于1000m"),
	C_40(40, "坐标点的误差范围：解析误差绝对精度小于2000m"),
	C_30(30, "坐标点的误差范围：解析误差绝对精度小于5000m"),
	C_25(25, "坐标点的误差范围：解析误差绝对精度小于8000m"),
	C_20(20, "坐标点的误差范围：解析误差绝对精度小于10000m"),
	;

	ConfidenceEnum(int code, String message) {
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

    public static ConfidenceEnum byCode(int code) {
        for (ConfidenceEnum enums : ConfidenceEnum.values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
