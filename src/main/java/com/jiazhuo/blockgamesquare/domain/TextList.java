package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 文本列表
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class TextList implements Serializable {
    private Long tid; //文本id
    private String title; //文本标题
    private Byte type; //文本类型 (0:常见问题 1:其它)
    private String text; //文本内容
}
