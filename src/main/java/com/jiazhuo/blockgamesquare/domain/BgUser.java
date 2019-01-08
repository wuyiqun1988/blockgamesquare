package com.jiazhuo.blockgamesquare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理人员
 */
@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
public class BgUser implements Serializable{
    private Long bid; //后台人员id
    private String username; //用户名
    private String realName; //姓名
    @JsonIgnore
    private String password; //密码
    private Byte state; //状态 (0:正常 2:禁用)
    private boolean admin; //是否管理员

    //关联角色
    private List<Role> roles = new ArrayList<>();
}