package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知
 */
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable{
    private Long nid; //通知id
    private String UID; //用户id
    private String title; //通知标题
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="GMT+8")
    private Date createTime; //消息时间
    private String notice; //通知内容
}