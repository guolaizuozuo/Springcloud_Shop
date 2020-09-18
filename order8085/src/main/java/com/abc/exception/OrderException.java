package com.abc.exception;

import com.abc.enums.ResultEnum;
import lombok.Getter;


@Getter
public class OrderException extends  RuntimeException{

    private Integer code;

    public OrderException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
