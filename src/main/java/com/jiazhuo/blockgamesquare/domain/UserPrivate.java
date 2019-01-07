package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 用户隐私资料表
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivate implements Serializable{
    private String UID;
    private String realName; //真实姓名
    private String IDCardNumber; //身份证号码
    private byte[] IDCardFront; //身份证正面
    private byte[] IDCardBack; //身份证反面
}