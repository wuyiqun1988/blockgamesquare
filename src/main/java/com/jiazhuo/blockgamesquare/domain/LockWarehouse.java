package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 锁仓记录
 */
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LockWarehouse implements Serializable{
    private Long LID; //锁仓id
    private String UID;
    private Double amount; //锁仓数量
    private Integer period; //锁仓周期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createdTime; //锁仓开始时间
    private Byte status; //状态
    private Byte tokenType; //锁仓类型
    private Double finalProfit; //每日收益
    private Integer profitTokenType; //收益类型

    private String nickName; //用户名
    private String phoneNumber; //手机号
}