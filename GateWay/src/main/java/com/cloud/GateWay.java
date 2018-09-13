package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 */
@EnableZuulProxy
@SpringBootApplication
public class GateWay {
    public static void main(String[] args) {
        SpringApplication.run(GateWay.class, args);
    }
}
