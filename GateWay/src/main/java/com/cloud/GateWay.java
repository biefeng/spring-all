package com.cloud;

import com.cloud.common.filter.CORSFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.Arrays;

/**
 * Hello world!
 */
@EnableZuulProxy
@SpringBootApplication
@MapperScan(basePackages = {"com.cloud"})
public class GateWay {
    @Autowired
    private CORSFilter corsFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(CORSFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setFilter(filter);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(GateWay.class, args);
    }
}
