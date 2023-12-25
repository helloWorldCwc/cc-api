package com.cc.ccapi.model.enums;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author cwc
 * @Date 2023/12/22 9:30
 * @Version 1.0
 */
public enum RequestMethodEnum {
    GET("GET","GET"),
    POST("POST","POST"),
    PUT("PUT","PUT"),
    DELETE("DELETE","DELETE"),
    PATCH("PATCH","PATCH")
    ;
    private String type;
    private String value;


    RequestMethodEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static RequestMethodEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (RequestMethodEnum anEnum : RequestMethodEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
