package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易记录
 几种转账类型：
 transferType:
 1.链上中心转账--链上钱包转到中心钱包
 2.链上链上转账--链上钱包转到链上钱包
 3.锁仓支出--链上钱包锁入中心钱包
 4.Dapp游戏支出
 5.购买代理人支出
 6.买EOS的RAM支出
 7.买EOS的CPU和NET支出
 8.提现（最小限额）--中心钱包转到链上钱包
 9.注册获得BGS
 10.邀请获得BGS
 11.锁仓获得收益
 12.代理获得收益
 */
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transfer implements Serializable{
    private Long transferId; //转账id
    private String UID;
    private String source; //源地址
    private String destination; //目标地址
    private Double amount; //数量
    private Byte transferType; //转账类型
    private Byte tokenType; //token类型(EOS/BGS/ETH)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createdTime; //交易时间
    private Byte status; //状态

    private UserBasic userBasic;
}