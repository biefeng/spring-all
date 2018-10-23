package com.cloud.main;

import com.cloud.ServiceSuply2;
import com.cloud.service.TestService;
import com.cloud.service.feign.ServiceSuply1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/30 2:15
 */

@Component
public class MainHandler {


    @Autowired
    private ServiceSuply1 serviceSuply1;

    public static void main(String[] args) {
        SpringApplication.run(ServiceSuply2.class);

        System.out.println(1);
    }
}
