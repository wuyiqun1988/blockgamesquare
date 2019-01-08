package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.IPermissionService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    /**
     * 加载权限
     * @param resp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mgrsite/permission/reload", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("加载权限")
    public JSONResultVo reload(HttpServletResponse resp) throws Exception {
        permissionService.reload();
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().print("{\"success\":true}");
        return JSONResultVo.ok("加载权限成功");
    }

    /**
     * 权限列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/permissionPage", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("权限列表")
    public JSONResultVo permissionPage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = permissionService.permissionPage(qo);
        vo.setResult(result);
        return vo;
    }

    /**
     * 删除权限
     * @param pid
     * @return
     */
    @RequestMapping(value = "/mgrsite/permission/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("删除权限")
    public JSONResultVo deletePermission(Long pid){
        if (pid != null){
            permissionService.delete(pid);
        }
        return JSONResultVo.ok("删除权限成功");
    }
}
