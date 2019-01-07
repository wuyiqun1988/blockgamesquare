package com.jiazhuo.blockgamesquare.util;


import com.jiazhuo.blockgamesquare.domain.BgUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 获取session
 */
public class UserContext {

    public static final String INITPASSWORD = "123456"; //初始化密码

    public static final String BGUSER_IN_SESSION = "loginInfo:"; //登录信息
    public static final String RESOURCE_IN_SESSION = "resource"; //权限表达式

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static void setCurrent(BgUser bgUser){
        getRequest().getSession().setAttribute(BGUSER_IN_SESSION, bgUser);
    }

    public static BgUser getCurrent(){
        return (BgUser) getRequest().getSession().getAttribute(BGUSER_IN_SESSION);
    }

    public static void setResource(List<String> resource){
        getRequest().getSession().setAttribute(RESOURCE_IN_SESSION, resource);
    }

    public static List<String> getResource(){
        return (List<String>) getRequest().getSession().getAttribute(RESOURCE_IN_SESSION);
    }

    public static String getIp() {
        return getRequest().getRemoteAddr();
    }
}
