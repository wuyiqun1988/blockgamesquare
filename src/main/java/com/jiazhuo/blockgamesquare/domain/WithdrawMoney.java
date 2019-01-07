package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 提现表
 */
@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor
public class WithdrawMoney implements Serializable{
    private String UID;
    private String WID; //提现id
    private Byte tokenType; //类型  1:ETH 2:BGS 3:EOS
    private Double amount; //提现金额
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createdTime; //申请时间
    private Byte status; //状态 0:待审核 1:审核成功 2:审核失败
    private String auditor; //审核人
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date auditTime; //审核时间
    private String remark; //审核备注

    private UserBasic userBasic; //用户基本信息
}