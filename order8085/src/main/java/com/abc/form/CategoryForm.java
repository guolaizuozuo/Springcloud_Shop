package com.abc.form;

import lombok.Data;

/**
 * @author Jiayuan
 * @version 1.0
 * @description:
 * @time 2018/8/4 20:31
 */
@Data
public class CategoryForm {
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;
}
