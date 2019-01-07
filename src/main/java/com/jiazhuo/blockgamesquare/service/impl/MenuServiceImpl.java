package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.mapper.MenuMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IMenuService;
import com.jiazhuo.blockgamesquare.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageResult menuPage(QueryObject qo) {
        int totalCount = menuMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = menuMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public void save(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public List<Menu> selectRootMenus() {
        return menuMapper.selectRootMenus();
    }

    @Override
    public List<Menu> selectBgUserMenus() {
        //查询所有的根菜单
        //获取当前的用户
        //判断当前用户是否是管理员
        //根据用户的id查询所有的菜单id集合
        List<Menu> rootMenus = selectRootMenus();
        BgUser currentUser = UserContext.getCurrent();
        if (currentUser.isAdmin()) {
            return rootMenus;
        }
        List<Long> menuIds = menuMapper.selectMenuIdsByBgUserId(currentUser.getBid());
        checkMenu(rootMenus, menuIds);
        return rootMenus;
    }

    /**
     * 在所有的菜单中过滤出拥有的菜单,如果不包含id,把对应的菜单删除
     * @param rootMenus
     * @param menuIds
     */
    private void checkMenu(List<Menu> rootMenus, List<Long> menuIds) {
        Iterator<Menu> it = rootMenus.iterator();
        while (it.hasNext()){
            Menu menu = it.next();
            if (!menuIds.contains(menu.getMid())) {
                it.remove();
            }
            //如果存在子节点,继续遍历处理子节点
            if (menu.getChildren() != null) {
                checkMenu(menu.getChildren(),menuIds);
            }
        }
    }

    @Override
    public List<Menu> selectChildrenMenus(Long mid) {
        List<Menu> menus = menuMapper.selectChildrenMenus(mid);
        return menus;
    }
}
