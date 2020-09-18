package com.abc.service;

import com.abc.common.DecreaseStockInput;
import com.abc.dataobject.ProductInfo;

import java.util.List;


public interface ProductService {

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param productIdList
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param decreaseStockInputList
     */
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);

    //List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList);
}
