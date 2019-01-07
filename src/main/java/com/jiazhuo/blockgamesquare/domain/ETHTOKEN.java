package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 中心化钱包ETH(BGS)
 */
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ETHTOKEN implements Serializable{

    //币种分类
    public static final int ETH_TOKEN_TYPE_ETH = 0;//ETH
    public static final int ETH_TOKEN_TYPE_BGS = 1;//BGS


    private String UID;
    private Integer type; //类型(0:ETH  1:BGS)
    private String ETHAddress; //ETH地址
    private Double amount; //数量
    private Byte canLock; //是否可以锁仓 0:不可以 1:可以

}