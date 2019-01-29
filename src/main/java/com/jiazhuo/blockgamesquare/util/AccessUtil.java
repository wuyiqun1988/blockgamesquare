package com.jiazhuo.blockgamesquare.util;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域访问工具类
 */
public class AccessUtil {

    public AccessUtil() {
    }

    public static void access(ServletResponse res){
        HttpServletResponse httpResponse = (HttpServletResponse) res;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
