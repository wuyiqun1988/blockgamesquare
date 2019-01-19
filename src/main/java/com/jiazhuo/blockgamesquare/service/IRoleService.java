package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface IRoleService {
    @SystemlogAnnotation("保存角色")
    void save(Role role, Long[] pids, Long[] mids);
    @SystemlogAnnotation("更新角色")
    void update(Role role, Long[] pids, Long[] mids);
    @SystemlogAnnotation("删除角色")
    void delete(Long rid);

    Role get(Long rid);
    List<Role> list();

    /**
     * 查看角色拥有的菜单id集合
     * @param rid
     * @return
     */
    List<Long> selectMenuIdsByRoleId(Long rid);

    /**
     * 查看角色关联的用户id集合
     * @param rid
     * @return
     */
    int selectBguserIdsByRoleId(Long rid);
}
