package com.abc.message;


import com.abc.common.ProductInfoOutput;
import com.abc.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
@Slf4j
public class ProductInfoReceiver {

    public final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private  ObjectMapper objectMapper;


    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //message => ProductOutput
        log.info("message: {}",message);
        System.out.println("-----");
        //暂未写
       // List<ProductInfoOutput> productInfoOutputs = JsonUtil.fromJson(message,ProductInfoOutput.class, ProductInfoOutput.class);
        List<ProductInfoOutput> productInfoOutputs = null;
        try {
            productInfoOutputs = Arrays.asList(objectMapper.readValue(message, ProductInfoOutput[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        //redis缓存
        for (ProductInfoOutput infoOutput:productInfoOutputs) {
            stringRedisTemplate.opsForHash().put("product_stock",infoOutput.getProductId(),infoOutput.getProductStock().toString());
        }
        /*stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),
                String.valueOf(productInfoOutput.getProductStock()));
*/
    }




}
