package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class Menu implements Serializable{
    private Long mid; //菜单id
    private String text; //菜单名称
    private String url; //链接
    private Menu parent; //父菜单

    //子菜单列表
    private List<Menu> children = new ArrayList<>();
}