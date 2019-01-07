package com.jiazhuo.blockgamesquare.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
public class JSONResultVo implements Serializable {
    private Boolean success = true;
    private String errorMsg;
    private Object result;

    public void setErrorMsg(String msg){
        this.success = false;
        this.errorMsg = msg;
    }
}
