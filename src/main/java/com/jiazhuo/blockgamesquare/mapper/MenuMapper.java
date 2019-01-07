package com.jiazhuo.blockgamesquare.mapper;

import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long mid);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long mid);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    /**
     * 根据父菜单查询子菜单
     * @param mid
     * @return
     */
    List<Menu> selectChildrenMenus(Long mid);

    int queryCount(QueryObject qo);

    List<Menu> queryList(QueryObject qo);

    /**
     * 查询根菜单
     * @return
     */
    List<Menu> selectRootMenus();

    /**
     * 查询当前用户拥有的菜单id集合
     * @param bid
     * @return
     */
    List<Long> selectMenuIdsByBgUserId(Long bid);

    /**
     * 查询角色拥有哪些菜单
     * @param rid
     * @return
     */
    List<Menu> selectMenusByRoleId(Long rid);
}