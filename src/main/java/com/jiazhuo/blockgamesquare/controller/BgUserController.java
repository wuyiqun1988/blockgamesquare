package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.BgUser;
import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
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
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo login(String username, String password){
        JSONResultVo vo = new JSONResultVo();
        try {
            bgUserService.login(username, password);
            vo.setResult("登录成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());

        }
        return vo;
    }

    /**
     * 获取后台用户列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/userList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo bgUserPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = bgUserService.bgUserPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 查看后台用户个人信息
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/info", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo userInfo(){
        JSONResultVo vo = new JSONResultVo();
        try {
            BgUser bgUser = bgUserService.get(UserContext.getCurrent().getBid());
            vo.setResult(bgUser);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 修改后台用户个人信息(用户修改)
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/update", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo userUpdate(String username){
        JSONResultVo vo = new JSONResultVo();
        try {
            if (bgUserService.checkUsername(username) != 0) {
                throw new DisplayableException("用户名已存在");
            }
            bgUserService.userUpdate(username, UserContext.getCurrent().getBid());
            vo.setResult("修改个人信息成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 修改后台用户密码(用户修改)
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/changePwd", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo changePwd(String oldPwd, String newPwd){
        JSONResultVo vo = new JSONResultVo();
        try {
            bgUserService.changePwd(UserContext.getCurrent().getBid(), oldPwd, newPwd);
            vo.setResult("修改密码成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 修改用户状态或分配管理员权限(管理员修改)
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/changeStatusOrAdmin", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员修改用户状态或分配管理员的权限")
    public JSONResultVo changeStatusOrAdmin(Long bid, int state, boolean admin){
        JSONResultVo vo = new JSONResultVo();
        try {
            bgUserService.changeStatusOrAdmin(bid, state, admin);
            vo.setResult("修改用户状态或分配管理员权限成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 重置密码(需要管理员来重置)
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("重置密码(管理员重置)")
    public JSONResultVo resetPwd(Long bid){
        JSONResultVo vo = new JSONResultVo();
        try {
            bgUserService.resetPwd(bid);
            vo.setResult("重置密码成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 后台管理员新建后台用户
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/newBguser", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员新建后台用户")
    public JSONResultVo newBguser(BgUser bgUser){
        JSONResultVo vo = new JSONResultVo();
        try {
            if (bgUserService.checkUsername(bgUser.getUsername()) != 0) {
                throw new DisplayableException("用户名已存在");
            }
            bgUserService.newBgUser(bgUser);
            vo.setResult("新建后台用户成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 管理员编辑用户角色
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/editBgUserRole", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("管理员编辑用户角色")
    public JSONResultVo editBgUserRole(Long bid){
        JSONResultVo vo = new JSONResultVo();
        try {
            BgUser bgUser = bgUserService.get(bid);
            List<Role> roles = roleService.list();
            Map<String, Object> map = new HashMap<>();
            map.put("bgUser", bgUser);
            map.put("roles", roles);
            vo.setResult(map);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 管理员分配用户角色
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/saveBgUserRole", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("管理员分配用户角色")
    public JSONResultVo saveBgUserRole(Long bid, Long[] rids){
        JSONResultVo vo = new JSONResultVo();
        try {
            bgUserService.saveBgUserRole(bid, rids);
            vo.setResult("分配角色成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 后台用户登出
     * @return
     */
    @RequestMapping(value = "/mgrsite/user/logout", method = RequestMethod.POST)
    @ResponseBody
    public JSONResultVo logout(HttpSession session){
        session.invalidate();
        JSONResultVo vo = new JSONResultVo();
        vo.setResult("登出成功");
        return vo;
    }
}
