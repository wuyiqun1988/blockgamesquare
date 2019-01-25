package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Permission;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
import com.jiazhuo.blockgamesquare.mapper.RoleMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IPermissionService;
import com.jiazhuo.blockgamesquare.util.PermissionUtil;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ApplicationContext ctx; //容器对象
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public void save(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void delete(Long pid) {
        permissionMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public List<Permission> list() {
        return permissionMapper.selectAll();
    }

    @Override
    public PageResult permissionPage(QueryObject qo) {
        int totalCount = permissionMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = permissionMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void reload() {
        //获取所有的权限表达式
        List<String> resources = permissionMapper.selectResources();
        //从容器中获取控制器类
        Collection<Object> ctrls = ctx.getBeansWithAnnotation(Controller.class).values();
        //获取控制器中的所有方法
        for (Object ctrl : ctrls) {
            Method[] ms = ctrl.getClass().getDeclaredMethods();
            //迭代方法,查看是否有权限注解
            for (Method m : ms) {
                String resource = PermissionUtil.buildResource(m);
                RequiredPermission anno = m.getAnnotation(RequiredPermission.class);
                if (anno != null && !resources.contains(resource)){
                    //赋值并保存到数据库中
                    Permission p = new Permission();
                    p.setName(anno.value());
                    p.setResource(resource);
                    permissionMapper.insert(p);
                    //把新的权限分配给角色
                    List<Role> roles = roleMapper.selectAll();
                    for (Role role : roles) {
                        roleMapper.insertPermissionRelation(role.getRid(), p.getPid());
                    }
                }
            }
        }
    }
}
