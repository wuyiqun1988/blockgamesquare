package com.jiazhuo.blockgamesquare.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiazhuo.blockgamesquare.domain.Inviter;
import com.jiazhuo.blockgamesquare.domain.UserStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgentVo implements Serializable {
    private String UID;
    private String nickName; //昵称
    private String phoneNumber; //电话号码 (+86)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date registerTime; //注册时间

    private int lowerAmount; //下级数量

    private Double totalIncome; //收益总额
}
