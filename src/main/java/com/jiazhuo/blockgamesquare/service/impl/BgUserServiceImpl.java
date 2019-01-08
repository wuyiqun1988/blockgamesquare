package com.jiazhuo.blockgamesquare.service.impl;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.mapper.BgUserMapper;
import com.jiazhuo.blockgamesquare.mapper.PermissionMapper;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IBgUserService;
import com.jiazhuo.blockgamesquare.util.SHA1;
import com.jiazhuo.blockgamesquare.util.UserContext;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
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
        //把当前用户拥有的权限表达式查询出来存入session中,用户后期权限的校验
        List<String> resources = permissionMapper.selectResourcesByBgUserId(bgUser.getBid());
        session.setAttribute(UserContext.RESOURCE_IN_SESSION, resources);
        return JSONResultVo.ok("登录成功");
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
    public boolean newBgUser(BgUser bgUser) {
        if (!UserContext.getCurrent().isAdmin()) {
            return false;
        }
        BgUser bu = new BgUser();
        bu.setAdmin(bgUser.isAdmin());
        bu.setPassword(SHA1.encode(bgUser.getPassword()));
        bu.setRealName(bgUser.getRealName());
        bu.setState(bgUser.getState());
        bu.setUsername(bgUser.getUsername());
        bgUserMapper.insert(bu);
        return true;
    }

    @Override
    public int checkUsername(String username) {
        return bgUserMapper.checkUsername(username);
    }

    @Override
    public boolean changeStatusOrAdmin(Long bid, int state, boolean admin) {
        if (!UserContext.getCurrent().isAdmin()) {
            return false;
        }
        bgUserMapper.changeStatusOrAdmin(bid, state, admin);
        return true;
    }

    @Override
    public void initAdmin() {
        int count = bgUserMapper.queryAdminCount();
        if (count == 0){
            BgUser bgUser = new BgUser();
            bgUser.setUsername("管理员");
            bgUser.setPassword(SHA1.encode(UserContext.DEFALUT_PASSWORD));
            bgUser.setRealName("管理员");
            bgUser.setAdmin(true);
            bgUser.setState((byte) 0);
            bgUserMapper.insert(bgUser);
        }
    }
}

