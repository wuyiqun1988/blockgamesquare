package com.jiazhuo.blockgamesquare.interceptor;

import com.gexin.fastjson.JSONObject;
import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.mapper.MenuMapper;
import com.jiazhuo.blockgamesquare.mapper.RoleMapper;
import com.jiazhuo.blockgamesquare.util.PermissionUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全控制拦截器
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //权限检验
        HttpSession session = request.getSession();
        BgUser bgUser = (BgUser) session.getAttribute(UserContext.BGUSER_IN_SESSION);
        //如果是管理员,直接放行
        if (bgUser.isAdmin()) {
            return true;
        }
        //拿到当前被访问的方法对象
        HandlerMethod hm = (HandlerMethod) handler;
        Method m = hm.getMethod();
        //判断当前用户是否有执行的权限
        String resource = PermissionUtil.buildResource(m); //构建权限表达式
        List<Long> menuIds = (List<Long>) session.getAttribute(UserContext.MENUIDS_IN_SESSION);
        List<List<String>> resourceList = new ArrayList<>();
        for (Long mid : menuIds) {
            List<String> resources = menuMapper.selectResources(mid); //获取拥有的权限表达式
            resourceList.add(resources);
        }
        if (resourceList.contains(resource)) {      //如果拥有权限,放行
            return true;
        }
        //如果没有权限,提示没有权限
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("errorMsg", "没有权限,请联系管理员!");
        JSONObject json = new JSONObject(map);
        returnJson(response, json);
        return false;
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
