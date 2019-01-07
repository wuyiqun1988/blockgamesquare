package com.jiazhuo.blockgamesquare.util;

import java.lang.reflect.Method;

//构建权限表达式 方法所在的类名:方法名
public abstract class PermissionUtil {
    public static String buildResource(Method m){
        String className = m.getDeclaringClass().getSimpleName();
        return className + ":" + m.getName();
    }
}
