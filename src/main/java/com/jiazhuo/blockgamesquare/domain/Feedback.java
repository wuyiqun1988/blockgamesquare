package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户反馈
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Feedback implements Serializable{
    private Long fid; //反馈id
    private String UID; //用户id
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime; //时间
    private String content; //反馈内容
    private String contact; //联系方式

    private UserBasic userBasic; //用户信息
}