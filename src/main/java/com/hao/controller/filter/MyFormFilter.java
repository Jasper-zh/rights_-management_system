package com.hao.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hao.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFormFilter extends FormAuthenticationFilter {

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMsg("认证成功");
        String s = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(s);
        return false;
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        String errorName = e.getClass().getName();
        if(errorName.equals(UnknownAccountException.class.getName())){
            ajaxRes.setMsg("账号不存在");
        }else if(errorName.equals(IncorrectCredentialsException.class.getName())){
            ajaxRes.setMsg("密码错误");
        }else {
            e.printStackTrace();
            ajaxRes.setMsg("未知错误");
        }
        try{
            String s = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(s);
        }catch (Exception loginError){

        }
        return false;
    }
}