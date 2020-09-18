package com.abc.enums;

import lombok.Getter;

/**
 * @author Jiayuan
 * @version 1.0
 * @description:
 * @time 2018/7/28 15:40
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
