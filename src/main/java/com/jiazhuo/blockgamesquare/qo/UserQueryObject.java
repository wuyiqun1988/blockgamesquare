package com.jiazhuo.blockgamesquare.qo;

import com.jiazhuo.blockgamesquare.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class UserQueryObject extends QueryObject {
    protected String keyword ; //关键字查询:手机号,用户名

    public String getKeyword(){
        return StringUtil.empty2null(keyword);
    }

}
