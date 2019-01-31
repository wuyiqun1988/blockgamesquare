package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户状态信息表
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserStatus implements Serializable{

    //用户的状态(正常/禁用)
    public static final Byte USER_NORMAL = 0; //正常
    public static final Byte USER_DISABLE = 1; //禁用

    private String UID;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updatedTime; //最近活跃时间

    private Byte state = USER_NORMAL; //状态(正常/禁用)
}