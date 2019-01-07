package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 广告轮播图
 */
@Getter@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    private Long bid; //轮播图id
    private String adName; //广告名称
    private Integer type; //类型
    private String photo; //图片
    private String textOfAd; //广告文本信息
    private String linkOfAd; //广告链接
    private Byte status; //状态
}