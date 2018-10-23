package com.cloud.common.config;

import com.cloud.shiro.CommRealm;
import com.cloud.shiro.filter.LoginFilter;
import com.cloud.shiro.filter.PermissionFilter;
import com.cloud.shiro.filter.RegistryFilter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.Filter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/8 22:06
 */

@Configuration
public class ShiroConfig {
    @Autowired
    private ShiroProperties shiroProp;

    @Autowired
    private LoginFilter loginFilter;

    @Autowired
    private RegistryFilter registryFilter;

    @Autowired
    private PermissionFilter permissionFilter;

    @Bean
    public SubjectFactory subjectFactory() {
        SubjectFactory subjectFactory = new DefaultWebSubjectFactory();
        return subjectFactory;
    }

    @Bean
    public SessionManager sessionManager() {
        SessionManager sessionManager = new DefaultWebSessionManager();
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(CommRealm commRealm, RememberMeManager rememberMeManager, SubjectFactory subjectFactory) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setSubjectFactory(subjectFactory);
        //set the realm
        securityManager.setRealm(commRealm);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //SecurityUtils.setSecurityManager(manager);
        //set the reuqired security manager
        shiroFilterFactoryBean.setSecurityManager(manager);

        Map<String, Filter> map = new HashMap<String, Filter>();
        map.put("login", loginFilter);
        map.put("registry", registryFilter);
        map.put("permission", permissionFilter);
        shiroFilterFactoryBean.setFilters(map);

        //filters
        List<String> filter = shiroProp.getFilter();

        Map<String, String> filterChainMap = new LinkedHashMap<>();

        for (String str : filter) {
            filterChainMap.put(str, "anon");
        }
        filterChainMap.put("/comm/registry", "registry");
        filterChainMap.put("/**", "login");
        //filterChainMap.put("/**", "permission");
        //set the logout
        filterChainMap.put(shiroProp.getLogout(), "logout");
        //filterChainMap.put(shiroProp.getAuthc(), "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        shiroFilterFactoryBean.setLoginUrl(shiroProp.getLoginUrl());
        shiroFilterFactoryBean.setSuccessUrl(shiroProp.getSuccessUrl());
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProp.getErrorUrl());

        return shiroFilterFactoryBean;
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    public PermissionFilter permissionFilter() {
        return new PermissionFilter();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        hashedCredentialsMatcher.setHashIterations(1024);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(false);
        return hashedCredentialsMatcher;
    }

    @Bean
    public CommRealm commRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        CommRealm commRealm = new CommRealm();
        commRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return commRealm;
    }

    @Bean
    public SimpleCookie simpleCookie() {
        //set the cookie name
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //set the cookie expire time (s)
        simpleCookie.setMaxAge(86400 * 30);
        return simpleCookie;
    }


    @Bean
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie cookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);
        String password = "2018101820181018";

        cookieRememberMeManager.setCipherKey(Base64.encode(password.getBytes()));

        return cookieRememberMeManager;
    }

    //enable the shiro annotation feature
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

   /* @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException","databaseError");
        mappings.setProperty("UnAuthorizedException","403");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);

        simpleMappingExceptionResolver.setDefaultErrorView("error");
        simpleMappingExceptionResolver.setExceptionAttribute("ex");

        return simpleMappingExceptionResolver;
    }*/
}
