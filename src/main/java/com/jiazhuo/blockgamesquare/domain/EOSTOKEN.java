package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 中心化钱包EOS
 */
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EOSTOKEN implements Serializable{
    //币种分类
    public static final int EOS_TOKEN_TYPE_EOS = 0;//EOS

    private String UID;
    private Integer type = EOS_TOKEN_TYPE_EOS; //类型
    private String EOSAccountName; //EOS地址
    private Double amount; //数量
    private Byte canLock;
}