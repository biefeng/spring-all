package com.cloud.service;

import com.cloud.dao.TestDao;
import com.cloud.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/8 12:05
 */
@Service
public class TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestDao dao;

    public String hello(String id){
        return dao.hello(id);
    }

    public void progresss(int index, int end) {

        ExecutorService service = ThreadPoolUtil.getService();
        int mark = 100;
        Task task;
        if((end-index)<mark){
            task = new Task(index,end);
            service.execute(task);

        }else {
            int threadCount = (end-index)/mark;
            for(int i=0;i<threadCount;i++){
                int tmp_index=index+mark*(i);
                int tmp_end;
                if(i<threadCount-1){
                    tmp_end = index+mark*(i+1);
                }else {
                    tmp_end=end;
                }
                task=new Task(tmp_index,tmp_end);
                service.execute(task);
            }
        }
    }

    private class Task implements Runnable{

        private int index;
        private int end;

        public Task(int index, int end) {
            this.index = index;
            this.end = end;
        }

        @Override
        public void run() {
            boolean running=true;
            LOGGER.info("----------INDEX: {} ,END: {}",index,end);

            while (index<end&&running){
                int record=0;
                for(int i=0;i<(end-index);i++){
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    index++;
                    record=i+1;
                }
                dao.progress("4267FA7F5DAE4BF98DB57D9F04D00F6F",record);
            }
        }
    }
}
