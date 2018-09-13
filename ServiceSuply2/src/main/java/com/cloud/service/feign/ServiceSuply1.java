package com.cloud.service.feign;

import com.cloud.module.ResponseEntity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/8 1:32
 */
@FeignClient("service-suply-1")
public interface ServiceSuply1 {

    @GetMapping("test/hello")
    public ResponseEntity hello(@RequestParam("id") String id);


    @GetMapping("test/progress")
    public ResponseEntity progress(@RequestParam("index")int index,@RequestParam("end") int end);

}
