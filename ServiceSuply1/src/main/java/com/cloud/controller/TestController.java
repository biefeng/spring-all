package com.cloud.controller;

import com.cloud.module.ResponseEntity;
import com.cloud.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/6 22:58
 */
@RestController
@RequestMapping("test")
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private TestService service;

    @GetMapping("hello")
    public ResponseEntity hello(@RequestParam("id") String id) {
        ResponseEntity entity = new ResponseEntity();
        entity.setSuccess(true);
        String nickName = service.hello(id);
        LOG.info("Hello : {}", nickName);
        entity.setData(nickName);
        return entity;
    }

    @GetMapping("progress")
    public ResponseEntity progress(@RequestParam("index") int index,
                                   @RequestParam("end") int end) {
        ResponseEntity entity = new ResponseEntity();
        try {
            service.progresss(index, end);
            entity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            entity.setSuccess(false);
            entity.setMessage(e.getMessage());
        }
        return entity;
    }

    @HystrixCommand(fallbackMethod = "error")
    public String testHystrix() {
        return "success";
    }

    public String error() {
        return "error";
    }


}
