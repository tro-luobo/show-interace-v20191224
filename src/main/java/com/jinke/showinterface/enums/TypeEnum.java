package com.jinke.showinterface.enums;


/**
 * 模块名称
 */
public enum TypeEnum {

    ZFHIGHT("ZFHIGHT", "珠穆朗玛峰");

    TypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static TypeEnum byCode(String code) {
        for (TypeEnum enums : TypeEnum.values()) {
            if (code.equalsIgnoreCase(enums.getCode())) {
                return enums;
            }
        }
        return null;
    }
}
