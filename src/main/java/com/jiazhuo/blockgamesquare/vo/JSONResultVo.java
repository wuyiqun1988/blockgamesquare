package com.jiazhuo.blockgamesquare.vo;

import lombok.*;

import java.io.Serializable;

@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor
public class JSONResultVo implements Serializable {
    private Boolean success = true;
    private String errorMsg;
    private Object result;

    public static JSONResultVo ok(String msg){
        return new JSONResultVo(true, null, msg);
    }

    public static JSONResultVo error(String errorMsg){
        return new JSONResultVo(false, errorMsg, null);
    }
}
