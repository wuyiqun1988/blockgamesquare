package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 */
@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor
public class Systemlog implements Serializable{
    private Long sid; //日志id
    private Long opuserId; //操作人id
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optime; //操作时间
    private String ipaddr; //操作ip
    private String function; //操作的方法
    private String opusername; //操作人
}