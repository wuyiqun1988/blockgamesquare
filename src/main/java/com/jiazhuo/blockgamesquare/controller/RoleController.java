package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Role;
import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IRoleService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {
    @Autowired
    private IRoleService roleService;

    /**
     * 角色列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/rolePage", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo rolePage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = roleService.rolePage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 删除角色
     * @return
     */
    @RequestMapping(value = "/mgrsite/role/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("删除角色")
    public JSONResultVo deleteRole(Long rid){
        JSONResultVo vo = new JSONResultVo();
        try {
            roleService.delete(rid);
            vo.setResult("删除角色成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 保存或更新角色
     * @return
     */
    @RequestMapping(value = "/mgrsite/role/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("保存或更新角色")
    public JSONResultVo saveOrUpdate(Role role, Long[] pids, Long[] mids){
        JSONResultVo vo = new JSONResultVo();
        try {
            if (role.getRid() == null){
                roleService.save(role, pids, mids);
            } else {
                roleService.update(role, pids, mids);
            }
            vo.setResult("保存或更新成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 查看角色拥有的菜单id集合
     * @return
     */
    @RequestMapping(value = "/mgrsite/role/menuIds", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo selectMenuIdsByRoleId(Long rid){
        JSONResultVo vo = new JSONResultVo();
        List<Long> menuIds = roleService.selectMenuIdsByRoleId(rid);
        Map<String, List<Long>> map = new HashMap<>();
        map.put("menuIds", menuIds);
        vo.setResult(map);
        return vo;
    }
}
