package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IBgUserService;
import com.jiazhuo.blockgamesquare.service.IRoleService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.UserContext;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BgUserController {
    @Autowired
    private IBgUserService bgUserService;
    @Autowired
    private IRoleService roleService;

    /**
     * 后台用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo login(String username, String password){
        JSONResultVo vo = bgUserService.login(username, password);
        return vo;
    }

    /**
     * 获取后台用户列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUserList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo bgUserPage(){
        JSONResultVo vo = new JSONResultVo();
        List<BgUser> result = bgUserService.queryAll();
        vo.setResult(result);
        return vo;
    }

    /**
     * 查看后台用户个人信息
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/info", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo userInfo(){
        JSONResultVo vo = new JSONResultVo();
        BgUser bgUser = bgUserService.get(UserContext.getCurrent().getBid());
        vo.setResult(bgUser);
        return vo;
    }

    /**
     * 修改后台用户个人信息(用户修改)
     * @param username
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo userUpdate(String username){
        if (bgUserService.checkUsername(username) != 0) {
            return JSONResultVo.error("用户名已存在");
        }
        boolean ret = bgUserService.userUpdate(username, UserContext.getCurrent().getBid());
        if (!ret){
            return JSONResultVo.error("您不是当前用户!");
        }
        return JSONResultVo.ok("修改个人信息成功");
    }

    /**
     * 修改后台用户密码(用户修改)
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/changePwd", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo changePwd(String oldPwd, String newPwd){
        boolean ret = bgUserService.changePwd(UserContext.getCurrent().getBid(), oldPwd, newPwd);
        if (!ret){
            return JSONResultVo.error("旧密码不正确");
        }
        return JSONResultVo.ok("修改密码成功");
    }

    /**
     * 修改用户状态(管理员修改)
     * @param bid
     * @param state
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员修改用户状态")
    public JSONResultVo changeStatus(Long bid, int state){
        boolean ret = bgUserService.changeStatus(bid, state);
        if (!ret){
            return JSONResultVo.error("不是管理员无权限");
        }
        return JSONResultVo.ok("修改用户状态成功");
    }

    /**
     * 重置密码(需要管理员来重置)
     * @param bid
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("重置密码(管理员重置)")
    public JSONResultVo resetPwd(Long bid){
        boolean ret = bgUserService.resetPwd(bid);
        if (!ret){
            return JSONResultVo.error("请联系管理员重置密码");
        }
        return JSONResultVo.ok("重置密码成功");
    }

    /**
     * 后台管理员新建后台用户
     * @param bgUser
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/newBguser", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员新建后台用户")
    public JSONResultVo newBguser(BgUser bgUser, Long rid){
        if (bgUserService.checkUsername(bgUser.getUsername()) != 0) {
            return JSONResultVo.error("用户名已存在");
        }
        boolean ret = bgUserService.newBgUser(bgUser, rid);
        if (!ret){
            return JSONResultVo.error("请联系管理员新建用户");
        }
        return JSONResultVo.ok("新建后台用户成功");
    }

    /**
     * 管理员编辑用户角色
     * @param bid
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/editBgUserRole", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("管理员编辑用户角色")
    public JSONResultVo editBgUserRole(Long bid){
        JSONResultVo vo = new JSONResultVo();
        BgUser bgUser = bgUserService.get(bid);
        List<Role> roles = roleService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("bgUser", bgUser);
        map.put("roles", roles);
        vo.setResult(map);
        return vo;
    }

    /**
     * 管理员分配用户角色
     * @param bid
     * @param rids
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/saveBgUserRole", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员分配用户角色")
    public JSONResultVo saveBgUserRole(Long bid, Long[] rids){
        bgUserService.saveBgUserRole(bid, rids);
        return JSONResultVo.ok("分配角色成功");
    }

    /**
     * 后台用户登出
     * @param session
     * @return
     */
    @RequestMapping(value = "/mgrsite/bgUser/logout", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo logout(HttpSession session){
        session.invalidate();
        return JSONResultVo.ok("登出成功");
    }
}
