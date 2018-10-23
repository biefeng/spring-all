package com.cloud.controller;

import com.cloud.common.config.ShiroProperties;
import com.cloud.module.ResponseEntity;
import com.cloud.module.UserPO;
import com.cloud.service.user.UserService;
import com.cloud.shiro.filter.FilterUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/14 20:44
 */
@RestController
@RequestMapping("comm")
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiroProperties prop;

    private static final Pattern passPattern = Pattern.compile("^(?:(?=\\w*[a-z])(?=\\w*[A-Z])(?=\\w*[a-zA-Z0-9]))\\w{8,}$");
    private static final Pattern userNamePattern = Pattern.compile("\\w{3,10}");

    /**
     * @return
     */
    @GetMapping("login")
    public ResponseEntity login(UserPO po, HttpServletRequest request, HttpServletResponse response) {


        ResponseEntity entity = new ResponseEntity();
        String userName = po.getUserName();
        String password = po.getPassword();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            entity.setMessage("用户名或密码不能为空！");
            entity.setSuccess(false);
            return entity;
        }
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);


        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, true);

        Subject subject = SecurityUtils.getSubject();

        subject.login(token);
        //FilterUtils.addCORSHeader(request,response);
        entity.setSuccess(true);
        entity.setMessage("login successful");
        return entity;
    }

    @PostMapping("registry")
    public ResponseEntity registry(UserPO po) {
        ResponseEntity entity = new ResponseEntity();

        String userName = po.getUserName();
        String password = po.getPassword();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            entity.setMessage("用户名或密码不能为空！");
            entity.setSuccess(false);
            return entity;
        }
        Matcher userNameMatcher = userNamePattern.matcher(userName);
        if (!userNameMatcher.matches()) {
            entity.setSuccess(false);
            entity.setMessage("用户名只能有数字，字母，下划线组成！");
            return entity;
        }
        if (password.length() < 8) {
            entity.setMessage("密码长度不能小于8！");
            entity.setSuccess(false);
            return entity;
        }
        Matcher passMatcher = passPattern.matcher(password);
        if (!passMatcher.matches()) {
            entity.setMessage("密码必须同时包含大小写！");
            entity.setSuccess(false);
            return entity;
        }

        RandomNumberGenerator generator = new SecureRandomNumberGenerator();
        ByteSource source = generator.nextBytes();

        String saltPassword = new Sha256Hash(po.getPassword(), source, 1024).toBase64();
        po.setPassword(saltPassword);
        po.setPasswordSalt(source.toBase64());

        userService.registry(po);
        entity.setSuccess(true);
        return entity;
    }

    @GetMapping("logout")
    public ResponseEntity logout() {
        ResponseEntity entity = new ResponseEntity();
        entity.setMessage("logout successfully");
        entity.setSuccess(true);
        return entity;
    }
}
