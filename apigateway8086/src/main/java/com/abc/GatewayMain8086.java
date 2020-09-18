package com.abc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayMain8086 {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain8086.class,args);
    }
}
