package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Permission;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface IPermissionService {

    void save(Permission permission);
    @SystemlogAnnotation("删除权限")
    void delete(Long pid);
    List<Permission> list();
    PageResult permissionPage(QueryObject qo);

    /**
     * 加载权限
     */
    void reload();
}
