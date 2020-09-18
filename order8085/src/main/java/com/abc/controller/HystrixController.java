package com.abc.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/** Hystrix服务降级与熔断
 */

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //设置超时时间，超时触发Hystrix降级
/*    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })*/

/*@HystrixCommand(commandProperties = {
        @HystrixProperty(name="circuitBreaker.enabled",value = "true"),
        @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
})*/

    @HystrixCommand
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("number")Integer number){
        if(number %2 == 0){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://127.0.0.1:8084/product/listForOrder",
                Arrays.asList("123456"),
                String.class);
    }

    private String fallback(){
        return "太拥挤了，请稍后再试~~";
    }
    private String defaultFallback(){
        return "默认提示：太拥挤了，请稍后再试~~";
    }
}
