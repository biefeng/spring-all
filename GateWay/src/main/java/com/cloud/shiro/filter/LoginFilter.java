package com.cloud.shiro.filter;

import com.cloud.common.config.ShiroProperties;
import com.cloud.module.UserPO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.apache.shiro.web.util.WebUtils.SAVED_REQUEST_KEY;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/18 21:06
 */


public class LoginFilter extends AccessControlFilter {

    @Autowired
    private ShiroProperties prop;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("-----------------login filter----------------");
        UsernamePasswordToken token = (UsernamePasswordToken) SecurityUtils.getSubject().getPrincipal();
        if(SecurityUtils.getSubject().isAuthenticated()){
            return true;
        }else if(((HttpServletRequest) request).getRequestURI().equals("/comm/login")){
            WebUtils.saveRequest(request);
            return true;
        }
        return false;
       // Subject subject = getSubject(request,response);
       // saveRequest(request);
/*
        SubjectContext subjectContext = new DefaultWebSubjectContext();
        ((DefaultWebSubjectContext) subjectContext).setServletRequest((HttpServletRequest)request);
        ((DefaultWebSubjectContext) subjectContext).setServletResponse((HttpServletResponse)response);

        WebSubject subject = (WebSubject) ThreadContext.getSubject();
        if(null == subject) {
           subject= (WebSubject) manager.createSubject(subjectContext);
           ThreadContext.bind(subject);
        }else {
            return true;
        }
        Session session = subject.getSession();
        SavedRequest savedRequest = new SavedRequest((HttpServletRequest) request);
        session.setAttribute(SAVED_REQUEST_KEY, savedRequest);*/



    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        Map<String,String> result = new HashMap<>();
        result.put("success","false");
        result.put("message","请先登录！");
        result.put("unAuthorized","true");
        result.put("location",prop.getLoginUrl());
        FilterUtils.returnJson(response,result);
        return false;
    }
}
