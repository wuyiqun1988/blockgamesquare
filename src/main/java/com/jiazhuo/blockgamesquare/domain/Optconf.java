package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Optconf implements Serializable {
    private String confName;
    private String confValue;

    private String explain; // 说明
}