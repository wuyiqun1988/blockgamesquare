package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Permission;
import com.jiazhuo.blockgamesquare.qo.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long pid);

    List<Permission> selectAll();

    int queryCount(QueryObject qo);

    List<Permission> queryList(QueryObject qo);

    /**
     * 查询权限表达式
     * @return
     */
    List<String> selectResources();

    /**
     * 根据后台人员id查询权限表达式
     * @param bid
     * @return
     */
    List<String> selectResourcesByBgUserId(Long bid);

    /**
     * 根据角色查询权限
     * @param rid
     * @return
     */
    List<Permission> selectPermissionsByRoleId(Long rid);

    /**
     * 查询所有权限id集合
     * @return
     */
    List<Long> selectAllPids();
}