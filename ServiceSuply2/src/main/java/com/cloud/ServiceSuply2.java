package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableFeignClients
@EnableCircuitBreaker
public class ServiceSuply2
{
    public static void main( String[] args )
    {
        SpringApplication.run(ServiceSuply2.class,args);
    }
}
