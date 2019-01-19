package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.Role;
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
    public JSONResultVo rolePage(){
        JSONResultVo vo = new JSONResultVo();
        List<Role> result = roleService.list();
        vo.setResult(result);
        return vo;
    }

    /**
     * 删除角色
     * @param rid
     * @return
     */
    @RequestMapping(value = "/mgrsite/role/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("删除角色")
    public JSONResultVo deleteRole(Long rid){
        int count = roleService.selectBguserIdsByRoleId(rid);
        if (count != 0){
            return JSONResultVo.error("该角色还有用户使用");
        }
        roleService.delete(rid);
        return JSONResultVo.ok("删除角色成功");
    }

    /**
     * 保存或更新角色
     * @param role
     * @param pids
     * @param mids
     * @return
     */
    @RequestMapping(value = "/mgrsite/role/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("保存或更新角色")
    public JSONResultVo saveOrUpdate(Role role, Long[] pids, String mids){
        String[] memus = mids.split(",");
        Long[] ms = new Long[memus.length];
        for (int idx = 0; idx < memus.length; idx++) {
            ms[idx] = Long.parseLong(memus[idx]);
        }
        if (role.getRid() == null){
            roleService.save(role, pids, ms);
            return JSONResultVo.ok("保存角色成功");
        } else {
            roleService.update(role, pids, ms);
            return JSONResultVo.ok("更新角色成功");
        }
    }

    /**
     * 查看角色拥有的菜单id集合
     * @param rid
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
