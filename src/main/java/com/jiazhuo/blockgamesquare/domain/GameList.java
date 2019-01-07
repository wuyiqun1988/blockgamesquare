package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏列表
 */
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GameList implements Serializable {
    //游戏类型
    public static final Byte GAME_ETH = 1; //eth游戏
    public static final Byte GAME_EOS = 2; //eos游戏
    //游戏状态
    public static final Byte GAME_ON= 1; //上架
    public static final Byte GAME_OFF = 0; //下架

    private Long gid; //游戏id
    private String gameName; //游戏名称
    private String link; //链接
    private Byte type; //类型
    private byte[] photo; //照片
    private String text; //文本
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date joindate; //接入时间
    private Byte status= GAME_OFF; //游戏状态
    private Byte sort; //排序
}