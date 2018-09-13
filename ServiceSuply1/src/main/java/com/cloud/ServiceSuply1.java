package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan(basePackages = {"com.cloud"})
@EnableScheduling
public class    ServiceSuply1 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceSuply1.class, args);
        System.out.println("Hello World!");
    }
}
