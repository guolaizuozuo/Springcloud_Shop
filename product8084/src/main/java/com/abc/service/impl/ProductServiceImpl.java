package com.abc.service.impl;

import com.abc.common.DecreaseStockInput;
import com.abc.common.ProductInfoOutput;
import com.abc.dataobject.ProductInfo;
import com.abc.enums.ProductStatusEnum;
import com.abc.enums.ResultEnum;
import com.abc.exception.SellException;
import com.abc.mapper.ProductInfoMapper;
import com.abc.service.ProductService;
import com.abc.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** 商品信息
 */
@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoMapper repository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
            List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);
            //发送mq消息
            List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e->{
                ProductInfoOutput output = new ProductInfoOutput();
                BeanUtils.copyProperties(e,output);
                return output;
            }).collect(Collectors.toList());
            amqpTemplate.convertAndSend("productInfo", JsonUtil.toJSON(productInfoOutputList));
        }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        for(DecreaseStockInput decreaseStockInput:decreaseStockInputList){
            Optional<ProductInfo> productInfoOptional  = repository.findById(decreaseStockInput.getProductId());
            //判断商品是否存在
            if(!productInfoOptional.isPresent()){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }

}
