package com.abc.controller;


import com.abc.client.ProductClient;
import com.abc.dataobject.ProductInfo;
import com.abc.dto.CartDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

   @Autowired
    private LoadBalancerClient loadBalancerClient;

/*   @Autowired
   private RestTemplate restTemplate;*/

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String msg(){
        //1.第一种方式（直接使用restTemplate，url写死
        /*RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8080/msg",String.class);
        log.info("response={}",response);*/

        //第二种方式（使用loadBalancerClient通过应用名获取url，然后再使用restTemplate
       /* RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort()+"/msg");
        String response = restTemplate.getForObject("http://localhost:8080/msg",String.class);
        log.info("response={}",response);*/

        //3.第三种方式(利用LoadBalanced,可在restTemplate里使用应用名字)
       // String response = restTemplate.getForObject("http://PRODUCT/msg",String.class);

        String response = productClient.productMsg();
        log.info("response={}",response);
        return response;
    }

    //yum中设置默认超时时间：方法上要加上@HystrixCommand，否则无效
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
//    })

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("157875196366160022", "164103465734242707"));
        log.info("response={}",productInfoList);
        return "ok";
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock(){
        productClient.decreaseStock(Arrays.asList(new CartDTO("123458",3)));
        return "ok";
    }
}
