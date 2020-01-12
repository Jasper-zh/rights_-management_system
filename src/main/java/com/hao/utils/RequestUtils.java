package com.hao.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static ThreadLocal<HttpServletRequest> local = new ThreadLocal<>();
    public static HttpServletRequest getRequest(){
        return local.get();
    }
    public static void setRequest(HttpServletRequest request){
        local.set(request);
    }
}
