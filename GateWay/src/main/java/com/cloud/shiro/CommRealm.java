package com.cloud.shiro;

import com.cloud.module.UserPO;
import com.cloud.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/8 23:28
 */

public class CommRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        UsernamePasswordToken token = (UsernamePasswordToken) getAvailablePrincipal(principalCollection);





        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        UserPO userPO = userService.login(token.getUsername());

        byte bytes[] = Base64.decode(userPO.getPasswordSalt());
        ByteSource salt = ByteSource.Util.bytes(bytes);
        return new SimpleAuthenticationInfo(token,userPO.getPassword(),salt,getName());
    }
}


