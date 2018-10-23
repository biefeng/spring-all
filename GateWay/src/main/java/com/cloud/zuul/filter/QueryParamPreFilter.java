package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/13 20:51
 */

/*

public class QueryParamPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER; //run before predecoration
    }

    @Override
    public boolean shouldFilter() {
        RequestContext rc = RequestContext.getCurrentContext();

      //  return !rc.contains(FORWARD_TO_KEY) //a filter has already forwarded
         //       &&!rc.contains(SERVICE_ID_KEY); //a filter has already determined serviceId
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        if (request.getParameter("sample") != null) {
            rc.put(SERVICE_ID_KEY, request.getParameter("foo"));
        }
        return null;
    }
}
*/
