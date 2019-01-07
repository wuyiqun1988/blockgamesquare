package com.jiazhuo.blockgamesquare.util;

import org.springframework.util.StringUtils;

/**
 * 判断参数是否为空
 */
public class StringUtil {
    public static String empty2null(String s){
        return StringUtils.hasLength(s) ? s : null;
    }

}
