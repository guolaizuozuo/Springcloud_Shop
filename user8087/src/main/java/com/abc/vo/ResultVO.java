package com.abc.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -8349181922768084604L;

    /** 错误码. */
    private Integer code;
    /** 提示信息. */
    private String msg;
    /* 具体内容. **/
    private T data;
}
