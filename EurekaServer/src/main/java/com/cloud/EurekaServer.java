package com.cloud;

import com.netflix.discovery.EurekaClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @Author : BieFeNg
 * @DATE : 2018/9/5 22:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaServer

public class EurekaServer {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class,args);

        System.out.println("click the url: "+EurekaClientConfigBean.DEFAULT_ZONE);
    }
}
