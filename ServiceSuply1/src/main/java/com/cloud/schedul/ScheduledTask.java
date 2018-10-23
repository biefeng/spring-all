package com.cloud.schedul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/10 21:48
 */
@EnableScheduling
@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    //@Scheduled(cron = "0/5 * * * * *")
    public void task1(){
        LOGGER.debug("Scheduled task is running!");
       // System.out.println("Scheduled task is running!");
    }
}
