package com.jiazhuo.blockgamesquare.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiazhuo.blockgamesquare.util.DateUtil;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter@Setter
public class SystemLogQueryObject extends QueryObject {
    protected String keyword ; //关键字查询:操作人 操作方法

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
