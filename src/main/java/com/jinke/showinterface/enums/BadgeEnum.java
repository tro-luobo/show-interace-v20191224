package com.jinke.showinterface.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * EQUALS 比较
 * EQUALSANDSECTION 比较加区间
 * SECTION	区间
 * EQUALSORSECTION 比较或者区间
 * DEF 默认
 * */
public enum BadgeEnum {
	USER_1("EQUALSANDSECTION", 0, 5, "金科之星","金科之星"),
	USER_2("SECTION", 0, 5, "", "金科新秀"),
	USER_3("SECTION", 5, 10, "", "金科栋梁"),
	USER_4("SECTION", 10, 999999, "", "金科元老"),
	
	SQTS_1("SECTION", 100, 999999, "", "爱岗敬业"),
	SQSJ_2("SECTION", 0, 3600, "", "雷厉风行"),
	SQXB_3("EQUALSORSECTION", 23, 9999999, "次日", "废寝忘食/夙兴夜寐"),
	
	BGSB_1("DEF", 0, 0, "", "披星戴月"),
	BGXB_2("SECTION", 23, 9999999, "", "废寝忘食"),
	BGHY_3("SECTION", 200, 9999999, "", "群策群力"),
	
	CC_1("DEF", 0, 0, "", "风雨兼程"),
	
	FP_1("DEF", 0, 0, "", "资深财务人"),
	
	FPYY_1("DEF", 0, 0, "", "法务能手"),
	
	XS_1("DEF", 0, 0, "", "销售大神"),
	
	XX_1("DEF", 0, 0, "", "IT大神"),
	
	ZJ_1("DEF", 0, 0, "", "金算盘"),
	
	JS_1("DEF", 0, 0, "", "金算盘"),
	
	FPHT_1("DEF", 0, 0, "", "合同达人"),
	;
	
	BadgeEnum(String type, int large, int small, String eq, String message) {
		this.type = type;
		this.large = large;
		this.small = small;
		this.eq = eq;
        this.message = message;
    }
	
	private String type;
    private int large;
    private int small;
    private String eq;
    private String message;

    public String getEq() {
		return eq;
	}

	public void setEq(String eq) {
		this.eq = eq;
	}

	public int getLarge() {
		return large;
	}

	public void setLarge(int large) {
		this.large = large;
	}

	public int getSmall() {
		return small;
	}

	public void setSmall(int small) {
		this.small = small;
	}

	public String getMessage() {
        return message;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setMessage(String message) {
        this.message = message;
    }

   /* public static BadgeEnum byCode(int code) {
        for (BadgeEnum enums : BadgeEnum.values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }*/
    
    public static List<BadgeEnum> getBadegeList(String likeKey){
    	List<BadgeEnum> list = new ArrayList<BadgeEnum>();
    	for (BadgeEnum enums : BadgeEnum.values()) {
            String type = enums.name().split("_")[0];
            if(type.equals(likeKey)) {
            	list.add(enums);
            }
        }
    	return list;
    }
}
