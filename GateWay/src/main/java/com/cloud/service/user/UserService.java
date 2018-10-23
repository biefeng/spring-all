package com.cloud.service.user;

import com.cloud.dao.UserDao;
import com.cloud.module.UserPO;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.nio.cs.US_ASCII;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/17 0:23
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * the login method
     * @param userName
     * @param password
     * @return
     */
    public UserPO login(String userName){
        return userDao.login(userName);
    }

    /**
     * registry
     * @param po
     */
    public void registry(UserPO po) {
        userDao.registry(po);
    }
}
