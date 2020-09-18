package com.abc.service;


import com.abc.dataobject.ProductCategory;

import java.util.List;

/** 类目
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
