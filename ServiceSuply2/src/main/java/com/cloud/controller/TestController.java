package com.cloud.controller;

import com.cloud.ServiceSuply2;
import com.cloud.module.ResponseEntity;
import com.cloud.service.TestService;
import com.cloud.service.feign.ServiceSuply1;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/6 22:58
 */
@RestController
@RequestMapping("test")
@CrossOrigin
public class TestController {

    @Autowired
    private TestService service;

    @Autowired
    private ServiceSuply1 serviceSuply1;

    public ResponseEntity test(){
        return new ResponseEntity();
    }

    @GetMapping("hello")
    @HystrixCommand(fallbackMethod = "error")
    public ResponseEntity hello(String id) {
        ResponseEntity entity = serviceSuply1.hello(id);
        entity.setSuccess(true);
        entity.setData("Hello world! get from service-suply-1");

        return entity;
    }

    @HystrixCommand(fallbackMethod = "error")
    public ResponseEntity testHystrix() {
        ResponseEntity entity = new ResponseEntity();
        entity.setSuccess(false);
        entity.setData("handle by testHystrix");
        return entity;
    }

    @GetMapping("progress")
    public ResponseEntity progress(){
        ResponseEntity entity = new ResponseEntity();
        int mark=5000;
        int total=43961;
        int requestCount = total/mark;
        for(int i=0;i<requestCount;i++){
            int tmp_index=i*mark;
            int tmp_end=0;
            if(i<requestCount-1){
                tmp_end=(i+1)*mark;
            }else {
                tmp_end=total;
            }
            serviceSuply1.progress(tmp_index,tmp_end);
        }
        entity.setSuccess(true);
        return entity;
    }

    public ResponseEntity error(String id) {
        ResponseEntity entity = new ResponseEntity();
        entity.setSuccess(false);
        entity.setData("handle by testHystrix");
        return entity;
    }


}
