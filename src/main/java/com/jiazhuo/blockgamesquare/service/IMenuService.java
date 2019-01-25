package com.jiazhuo.blockgamesquare.service;

import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.util.SystemlogAnnotation;

import java.util.List;

public interface IMenuService {
    PageResult menuPage(QueryObject qo);
    @SystemlogAnnotation("保存菜单")
    void save(Menu menu);
    @SystemlogAnnotation("更新菜单")
    void update(Menu menu);

    /**
     * 查询根菜单
     * @return
     */
    List<Menu> selectRootMenus();

    /**
     * 查询当前用户拥有的菜单
     * @return
     */
    List<Menu> selectBgUserMenus();

    /**
     * 查询子菜单
     * @param mid
     * @return
     */
    List<Menu> selectChildrenMenus(Long mid);

    /**
     * 初始化菜单和权限的关系
     */
    void initMenuPermission();
}
