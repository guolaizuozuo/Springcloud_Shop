package com.abc.enums;

import lombok.Getter;

/**
 * @author Jiayuan
 * @version 1.0
 * @description:
 * @time 11/9/2018 9:31 AM
 */

@Getter
public enum RoleEnum {
    BUYER(1,"买家"),
    SELLER(2,"卖家"),
    ;
    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
