package com.cloud.shiro.filter;

import com.google.gson.Gson;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/21 1:30
 */
public class FilterUtils {

    public static void returnJson(ServletResponse response,Object obj){
        try(OutputStream out = response.getOutputStream()){
            Gson gson = new Gson();

            String str = gson.toJson(obj);
            out.write(str.getBytes("utf-8"));
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addCORSHeader(ServletRequest request,ServletResponse response){
        response.setCharacterEncoding("utf-8");
        ((HttpServletResponse)response).addHeader("content-type", "text/html;charset=utf-8");
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Origin",((HttpServletRequest)request).getHeader("origin"));
        ((HttpServletResponse)response).addHeader("Access-Control-Allow-Credentials","true");
    }
}
