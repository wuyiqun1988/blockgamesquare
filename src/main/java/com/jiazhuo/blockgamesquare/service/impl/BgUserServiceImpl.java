package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Menu;
import com.jiazhuo.blockgamesquare.domain.Permission;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.mapper.BgUserMapper;
import com.jiazhuo.blockgamesquare.mapper.MenuMapper;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
import com.jiazhuo.blockgamesquare.mapper.RoleMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBgUserService;
import com.jiazhuo.blockgamesquare.service.IMenuService;
import com.jiazhuo.blockgamesquare.service.IPermissionService;
import com.jiazhuo.blockgamesquare.util.SHA1;
import com.jiazhuo.blockgamesquare.util.UserContext;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BgUserServiceImpl implements IBgUserService {
    @Autowired
    private BgUserMapper bgUserMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public void saveBgUserRole(Long bid, Long rid) {
        //先删除关联关系.再保存
        bgUserMapper.deleteRoleRelation(bid);
        bgUserMapper.insertRoleRelation(bid, rid);
    }

    @Override
    public void delete(Long bid) {
        //先打破关联关系
        bgUserMapper.deleteRoleRelation(bid);
        bgUserMapper.deleteByPrimaryKey(bid);
    }

    @Override
    public BgUser get(Long bid) {
        return bgUserMapper.selectByPrimaryKey(bid);
    }

    @Override
    public PageResult bgUserPage(QueryObject qo) {
        int totalCount = bgUserMapper.queryCount(qo);
        if (totalCount == 0){
            return PageResult.empty();
        }
        List data = bgUserMapper.queryList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    @Override
    public JSONResultVo login(String username, String password) {
        BgUser bgUser = bgUserMapper.selectBgUserByInfo(username, SHA1.encode(password));
        if (bgUser == null) {
            return JSONResultVo.error("账号和密码不匹配");
        }
        if (bgUser.getState() == 1){
            return JSONResultVo.error("您已被禁用,请联系管理员");
        }
        HttpSession session = UserContext.getRequest().getSession();
        //把当前登录成功的用户保存到session中
        session.setAttribute(UserContext.BGUSER_IN_SESSION, bgUser);
        session.setMaxInactiveInterval(30 * 60);
        //把当前用户拥有的菜单查询出来放入session中
        List<Role> roles = bgUser.getRoles();
        for (Role role : roles) {
            List<Long> menuIds = roleMapper.selectMenuIdsByRoleId(role.getRid());
            session.setAttribute(UserContext.MENUIDS_IN_SESSION, menuIds);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("bgUser", bgUser);
        map.put("menuIds", session.getAttribute(UserContext.MENUIDS_IN_SESSION));
        JSONResultVo vo = new JSONResultVo();
        vo.setResult(map);
        return vo;
    }

    @Override
    public boolean userUpdate(String username, Long bid) {
        if (UserContext.getCurrent().getBid() != bid) {
            return false;
        }
        bgUserMapper.updateUserInfo(username, bid);
        return true;
    }

    @Override
    public boolean changePwd(Long bid, String oldPwd, String newPwd) {
        if (bgUserMapper.checkOldPwd(bid, SHA1.encode(oldPwd)) == 0){   //判断旧密码是否正确
            return false;
        }
        bgUserMapper.changePwd(bid, SHA1.encode(newPwd));
        return true;
    }

    @Override
    public boolean resetPwd(Long bid) {
        if (!UserContext.getCurrent().isAdmin()) {
            return false;
        }
        String initpassword = UserContext.DEFALUT_PASSWORD;
        bgUserMapper.resetPwd(bid, SHA1.encode(initpassword));
        return true;
    }

    @Override
    public boolean newBgUser(BgUser bgUser, Long rid) {
        if (!UserContext.getCurrent().isAdmin()) {
            return false;
        }
        BgUser bu = new BgUser();
        bu.setAdmin(bgUser.isAdmin());
        bu.setPassword(SHA1.encode(bgUser.getPassword()));
        bu.setRealName(bgUser.getRealName());
        bu.setState(bgUser.getState());
        bu.setUsername(bgUser.getUsername());
        List<Role> roles = new ArrayList<>();
        Role role = roleMapper.selectByPrimaryKey(rid);
        roles.add(role);
        bu.setRoles(roles);
        bgUserMapper.insert(bu);
        //插入关联关系
        bgUserMapper.insertRoleRelation(bu.getBid(), rid);
        return true;
    }

    @Override
    public int checkUsername(String username) {
        return bgUserMapper.checkUsername(username);
    }

    @Override
    public boolean changeStatus(Long bid, Byte state) {
        if (!UserContext.getCurrent().isAdmin()) {
            return false;
        }
        bgUserMapper.changeStatus(bid, state);
        return true;
    }

    @Override
    public void initAdmin() {
        int count = bgUserMapper.queryAdminCount();
        if (count == 0){
            BgUser bgUser = new BgUser();
            bgUser.setUsername("admin");
            bgUser.setPassword(SHA1.encode(UserContext.DEFALUT_PASSWORD));
            bgUser.setRealName("管理员");
            bgUser.setAdmin(true);
            bgUser.setState((byte) 0);
            bgUserMapper.insert(bgUser);
            Role role = new Role();
            role.setName("一级管理员");
            role.setSn("ADMIN");
            roleMapper.insert(role);
            bgUserMapper.insertRoleRelation(bgUser.getBid(), role.getRid());   //保存用户和角色的关系
            List<Menu> menus = menuMapper.selectAll();
            for (Menu menu : menus) {   //保存角色和菜单的关系
                roleMapper.insertMenuRelation(role.getRid(), menu.getMid());
            }
            permissionService.reload(); //加载权限
            List<Permission> permissions = permissionMapper.selectAll();
            for (Permission permission : permissions) {  //保存角色和权限的关系
                roleMapper.insertPermissionRelation(role.getRid(), permission.getPid());
            }

        }
    }

    @Override
    public List<BgUser> queryAll() {
        return bgUserMapper.selectAll();
    }
}

