package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * 根据后台人员查询角色
     * @param bid
     * @return
     */
    List<Role> selectRolesByUgUserID(Long bid);

    int queryCount(QueryObject qo);

    List<Role> queryList(QueryObject qo);

    /**
     * 删除权限和角色的关系
     * @param rid
     */
    void deletePermissionRelation(Long rid);

    /**
     * 插入权限和角色的关系
     * @param rid
     * @param pid
     */
    void insertPermissionRelation(@Param("rid") Long rid, @Param("pid") Long pid);

    /**
     * 查看角色拥有的菜单id集合
     * @param rid
     * @return
     */
    List<Long> selectMenuIdsByRoleId(Long rid);

    /**
     * 插入菜单和角色的关系
     * @param rid
     * @param mid
     */
    void insertMenuRelation(@Param("rid") Long rid, @Param("mid") Long mid);

    /**
     * 删除角色和菜单的关系
     * @param rid
     */
    void deleteMenuRelation(Long rid);

    /**
     * 查看角色关联的用户id集合
     * @param rid
     * @return
     */
    int selectBguserIdsByRoleId(Long rid);

}