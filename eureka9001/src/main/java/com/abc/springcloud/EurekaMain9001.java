package com.abc.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaMain9001.class,args);
    }
}
