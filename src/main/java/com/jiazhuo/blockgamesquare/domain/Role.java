package com.jiazhuo.blockgamesquare.domain;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 */
@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor
public class Role implements Serializable{
    private Long rid; //角色id
    private String sn; //编码
    private String name; //角色名称

    //关联权限
    private List<Permission> permissions = new ArrayList<>();
    //关联菜单
    private List<Menu> menus = new ArrayList<>();
}