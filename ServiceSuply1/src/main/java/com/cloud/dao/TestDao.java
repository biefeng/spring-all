package com.cloud.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/8 12:05
 */
public interface TestDao {
    public String hello(@Param("id") String id);

    void progress(@Param("id") String id,@Param("count") int count);
}
