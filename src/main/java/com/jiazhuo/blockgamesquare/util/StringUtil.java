package com.jiazhuo.blockgamesquare.util;

import org.springframework.util.StringUtils;

/**
 * 判断参数是否为空
 */
public class StringUtil {
    public static String empty2null(String s){
        return StringUtils.hasLength(s) ? s : null;
    }

    public static boolean isNull(String param){
        if (param == null || "".equals(param.trim())){
            return true;
        }
        return false;
    }

    public static boolean isNull(Long param){
        if (param == null){
            return true;
        }
        return false;
    }

    public static boolean isNull(Byte param){
        if (param == null){
            return true;
        }
        return false;
    }

    public static boolean isNull(Boolean param){
        if (param == null){
            return true;
        }
        return false;
    }

}
