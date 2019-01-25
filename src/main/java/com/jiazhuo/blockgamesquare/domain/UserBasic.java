package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户基本资料表
 */
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserBasic implements Serializable{

    //用户性别
    public static final Byte SEX_MAN = 1; //男
    public static final Byte SEX_WOMAN = 0; //女

    private String UID;
    private String nickName; //昵称
    private Byte sex; //性别  默认未设置
    private Byte isAgency; //是否是代理人 默认不是代理人
    private String phoneNumber; //电话号码 (+86)
    private String inviter; //邀请人是谁
    private Byte status ; //状态
    @JsonIgnore
    private String passWord; //登录密码
    @JsonIgnore
    private String payPassWord; //支付密码
    private String invitedCode; //邀请码
    private String headPhoto; // 头像
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date registerTime; //注册时间

    private Byte state; //用户状态
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date invitingTime; //被邀请时间

}