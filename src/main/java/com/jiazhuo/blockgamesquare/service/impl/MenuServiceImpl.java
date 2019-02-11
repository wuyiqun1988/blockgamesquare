package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.domain.Permission;
import com.jiazhuo.blockgamesquare.mapper.MenuMapper;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
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
    @Autowired
    private PermissionMapper permissionMapper;

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

    @Override
    public void initMenuPermission() {
        int count = menuMapper.selectMenuPermissionRelation();
        if (count == 0){
            //获取所有的根菜单
            List<Menu> menus = menuMapper.selectRootMenus();
            //获取所有的权限
            List<Permission> permissions = permissionMapper.selectAll();
            for (Menu menu : menus) {
                if (menu.getMid() == 2){    //用户信息
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("Agent") ||
                                permission.getResource().startsWith("User")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 3){    //游戏管理
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("Game")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 4){    //链上钱包
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("Chain")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 5){    //中心钱包
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("Cent")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 6){    //提现管理
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("WithdrawMoney")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 7){    //锁仓管理
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("Lock")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
                if (menu.getMid() == 8){    //系统管理
                    for (Permission permission : permissions) {
                        if (permission.getResource().startsWith("BgUser") ||
                                permission.getResource().startsWith("Conf") ||
                                permission.getResource().startsWith("App") ||
                                permission.getResource().startsWith("Menu") ||
                                permission.getResource().startsWith("Permission")||
                                permission.getResource().startsWith("Role") ||
                                permission.getResource().startsWith("SystemLog") ||
                                permission.getResource().startsWith("TextList")){
                            menuMapper.insertMenuPermissionRelation(menu.getMid(), permission.getPid());
                        }
                    }
                }
            }
        }

    }
}
