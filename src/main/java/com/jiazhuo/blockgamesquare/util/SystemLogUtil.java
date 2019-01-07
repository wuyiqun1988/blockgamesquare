package com.jiazhuo.blockgamesquare.util;

import com.jiazhuo.blockgamesquare.domain.Systemlog;
import com.jiazhuo.blockgamesquare.service.ISystemlogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
public class SystemLogUtil {
    @Autowired
    private ISystemlogService systemlogService;

    public void write(JoinPoint joinPoint){
        //如果是日志的service,直接放行
        if (joinPoint.getTarget() instanceof ISystemlogService) {
            return;
        }
        /*//获取当前访问的类名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        //获取方法名称
        String methodName = joinPoint.getSignature().getName();*/
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null){
            SystemlogAnnotation anno = method.getAnnotation(SystemlogAnnotation.class);
            if (anno != null) {
                Systemlog log = new Systemlog();
                //开始进行日志记录相关操作
                //获取操作人的id,用户名
                Long bid = UserContext.getCurrent().getBid();
                String opusername = UserContext.getCurrent().getUsername();
                //获取IP地址
                String ip = UserContext.getIp();
                //设置操作人id,操作时间,操作ip,操作的方法
                log.setOpuserId(bid);
                log.setOptime(new Date());
                log.setIpaddr(ip);
                log.setFunction(anno.value());
                log.setOpusername(opusername);
                systemlogService.write(log);
            }
        }
    }
}
