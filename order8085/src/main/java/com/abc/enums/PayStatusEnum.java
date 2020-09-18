package com.abc.enums;

import lombok.Getter;

/**
 * @author Jiayuan
 * @version 1.0
 * @description:
 * @time 2018/7/28 15:40
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),

    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
