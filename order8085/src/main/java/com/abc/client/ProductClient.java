package com.abc.client;


import com.abc.dataobject.ProductInfo;
import com.abc.dto.CartDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/** 商品服务客户端
 * @author 过来坐坐
 */

@FeignClient(
        name="PRODUCT",
        fallback = ProductClient.ProductClientFallback.class
    )
public interface ProductClient {

    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);

    @Component
    public static class ProductClientFallback implements ProductClient{

        @Override
        public String productMsg() {
            return null;
        }

        @Override
        public List<ProductInfo> listForOrder(List<String> productIdList) {
            System.out.println("--listForOrder-降容--");
            return null;
        }

        @Override
        public void decreaseStock(List<CartDTO> cartDTOList) {

        }
    }
}
