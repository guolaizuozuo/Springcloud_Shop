package com.abc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class ProductInfoVO implements Serializable{

    private static final long serialVersionUID = -1238738795665080115L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDesriction;

    @JsonProperty("icon")
    private String productIcon;
}
