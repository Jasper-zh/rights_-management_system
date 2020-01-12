package com.hao.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hao.domain.Systemlog;
import com.hao.mapper.SystemlogMapper;
import com.hao.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.event.ObjectChangeListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SystemAspect {
    @Autowired
    SystemlogMapper systemlogMapper;
    public void writeLog(JoinPoint point) throws JsonProcessingException {
        HttpServletRequest request = RequestUtils.getRequest();
        //创建日志数据模型
        Systemlog systemlog = new Systemlog();
        //获取操作时间
        systemlog.setOptime(new Date());
        //获取操作ip
        try{
            systemlog.setIp(request.getRemoteAddr());
        }catch (Exception e){
            System.out.println("");
        }
        //获取方法信息
        String name = point.getTarget().getClass().getName() + ":" + point.getSignature().getName();
        String params = new ObjectMapper().writeValueAsString(point.getArgs());
        systemlog.setFunction(name);
        systemlog.setParams(params);
        //保存
        systemlogMapper.insert(systemlog);
    }
}
