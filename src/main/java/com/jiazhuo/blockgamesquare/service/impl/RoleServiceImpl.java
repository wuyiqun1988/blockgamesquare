package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
import com.jiazhuo.blockgamesquare.mapper.RoleMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public void save(Role role, Long[] pids, Long[] mids) {
        roleMapper.insert(role);
        //保存中间表的关系
        List<Long> allPids = permissionMapper.selectAllPids();   //默认添加所有权限
        pids = allPids.toArray(new Long[allPids.size()]);
        savePermissionRelation(role.getRid(), pids);
        //保存菜单和角色的关系
        saveRoleRelation(role.getRid(), mids);
    }



    @Override
    public void update(Role role, Long[] pids, Long[] mids) {
        //打破角色和权限的关系
        roleMapper.deletePermissionRelation(role.getRid());
        //打破角色和菜单的关系
        roleMapper.deleteMenuRelation(role.getRid());
        //保存角色和权限的关系
        List<Long> allPids = permissionMapper.selectAllPids();   //默认添加所有权限
        pids = allPids.toArray(new Long[allPids.size()]);
        savePermissionRelation(role.getRid(), pids);
        //保存角色和菜单的关系
        saveRoleRelation(role.getRid(), mids);
    }

    private void savePermissionRelation(Long rid, Long[] pids) {
        if (pids != null) {
            for (Long pid : pids) {
                roleMapper.insertPermissionRelation(rid, pid);
            }
        }
    }

    private void saveRoleRelation(Long rid, Long[] mids) {
        if (mids != null){
            for (Long mid : mids) {
                roleMapper.insertMenuRelation(rid, mid);
            }
        }
    }

    @Override
    public void delete(Long rid) {
        //先删除中间表的关系
        roleMapper.deletePermissionRelation(rid);
        roleMapper.deleteMenuRelation(rid);
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public Role get(Long rid) {
        return roleMapper.selectByPrimaryKey(rid);
    }

    @Override
    public List<Role> list() {
        return roleMapper.selectAll();
    }


    @Override
    public List<Long> selectMenuIdsByRoleId(Long rid) {
        return roleMapper.selectMenuIdsByRoleId(rid);
    }

    @Override
    public int selectBguserIdsByRoleId(Long rid) {
        return roleMapper.selectBguserIdsByRoleId(rid);
    }
}
