package com.cloud.common.filter;

import com.cloud.shiro.filter.FilterUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/21 3:17
 */
@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterUtils.addCORSHeader(request,response);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
