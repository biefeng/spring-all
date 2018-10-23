package com.cloud.dao;

import com.cloud.module.UserPO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/18 21:49
 */
public interface UserDao {
    /**
     * registry
     * @param po
     */
    void registry(@Param("po") UserPO po);

    /**
     * login
     * @param userName
     * @return
     */
    UserPO login(@Param("userName") String userName);
}
