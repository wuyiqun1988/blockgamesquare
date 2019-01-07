package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;

/**
 * 权限
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Permission implements Serializable{
    private Long pid; //权限id
    private String name; //权限名称
    private String resource; //权限表达式
}