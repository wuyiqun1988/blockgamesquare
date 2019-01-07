package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.mapper.BgUserMapper;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBgUserService;
import com.jiazhuo.blockgamesquare.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class BgUserServiceImpl implements IBgUserService {
    @Autowired
    private BgUserMapper bgUserMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void saveBgUserRole(Long bid, Long[] rids) {
        //先删除关联关系.再保存
        bgUserMapper.deleteRoleRelation(bid);
        saveRelation(bid, rids);
    }

    private void saveRelation(Long bid, Long[] rids) {
        if (rids != null) {
            for (Long rid : rids) {
                bgUserMapper.insertRoleRelation(bid, rid);
            }
        }
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
    public void login(String username, String password) {
        BgUser bgUser = bgUserMapper.selectBgUserByInfo(username, password);
        if (bgUser == null) {
            throw new DisplayableException("账号和密码不匹配");
        }
        if (bgUser.getState() == 1){
            throw new DisplayableException("您已被禁用,请联系管理员");
        }
        HttpSession session = UserContext.getRequest().getSession();
        //把当前登录成功的用户保存到session中
        session.setAttribute(UserContext.BGUSER_IN_SESSION, bgUser);
        //把当前用户拥有的权限表达式查询出来存入session中,用户后期权限的校验
        List<String> resources = permissionMapper.selectResourcesByBgUserId(bgUser.getBid());
        session.setAttribute(UserContext.RESOURCE_IN_SESSION, resources);
    }

    @Override
    public void userUpdate(String username, Long bid) {
        if (UserContext.getCurrent().getBid() != bid) {
            throw new DisplayableException("您不是当前用户!");
        }
        bgUserMapper.updateUserInfo(username, bid);
    }

    @Override
    public void changePwd(Long bid, String oldPwd, String newPwd) {
        if (bgUserMapper.checkOldPwd(bid, oldPwd) == 0){   //判断旧密码是否正确
            throw new DisplayableException("旧密码不正确");
        }
        bgUserMapper.changePwd(bid, newPwd);
    }

    @Override
    public void resetPwd(Long bid) {
        if (!UserContext.getCurrent().isAdmin()) {
            throw new DisplayableException("请联系管理员重置密码");
        }
        String initpassword = UserContext.INITPASSWORD;
        bgUserMapper.resetPwd(bid, initpassword);
    }

    @Override
    public void newBgUser(BgUser bgUser) {
        if (!UserContext.getCurrent().isAdmin()) {
            throw new DisplayableException("请联系管理员新建用户");
        }
        BgUser bu = new BgUser();
        bu.setAdmin(bgUser.isAdmin());
        bu.setPassword(bgUser.getPassword());
        bu.setRealName(bgUser.getRealName());
        bu.setState(bgUser.getState());
        bu.setUsername(bgUser.getUsername());
        bgUserMapper.insert(bu);
    }

    @Override
    public int checkUsername(String username) {
        return bgUserMapper.checkUsername(username);
    }

    @Override
    public void changeStatusOrAdmin(Long bid, int state, boolean admin) {
        if (!UserContext.getCurrent().isAdmin()) {
            throw new DisplayableException("不是管理员无权限");
        }
        bgUserMapper.changeStatusOrAdmin(bid, state, admin);
    }
}

