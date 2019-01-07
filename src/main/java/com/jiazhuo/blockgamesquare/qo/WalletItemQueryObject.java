package com.jiazhuo.blockgamesquare.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter@Setter
public class WalletItemQueryObject extends QueryObject {
    protected String keyword ; //关键字查询:手机号,用户名
    private Byte transferType; //转账类型

    public String getKeyword(){
        return StringUtil.empty2null(keyword);
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date beginDate; //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endDate; //结束时间

    public Date getEndDate(){
        return DateUtil.getEndTime(endDate);
    }
}
