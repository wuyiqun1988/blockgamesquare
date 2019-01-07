package com.jiazhuo.blockgamesquare.interceptor;

import com.gexin.fastjson.JSONObject;
import com.jiazhuo.blockgamesquare.util.UserContext;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object bgUser = request.getSession().getAttribute(UserContext.BGUSER_IN_SESSION);
        //没有登录
        if (bgUser == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("errorMsg", "请登录");
            JSONObject json = new JSONObject(map);
            returnJson(response, json);
            return false;
        }
        return true;
    }
    private void returnJson(HttpServletResponse response, JSONObject json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            System.out.print(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
