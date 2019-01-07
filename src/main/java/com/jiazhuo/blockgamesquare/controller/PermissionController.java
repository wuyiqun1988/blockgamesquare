package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.exception.DisplayableException;
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
     * @return
     */
    @RequestMapping(value = "/mgrsite/permission/reload", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("加载权限")
    public JSONResultVo reload(HttpServletResponse resp) throws Exception {
        JSONResultVo vo = new JSONResultVo();
        permissionService.reload();
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().print("{\"success\":true}");
        vo.setResult("加载权限成功");
        return null;
    }

    /**
     * 权限列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/permissionPage", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("权限列表")
    public JSONResultVo permissionPage(@ModelAttribute("qo") QueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = permissionService.permissionPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 删除权限
     * @return
     */
    @RequestMapping(value = "/mgrsite/permission/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("删除权限")
    public JSONResultVo deletePermission(Long pid){
        JSONResultVo vo = new JSONResultVo();
        try {
            if (pid != null){
                permissionService.delete(pid);
            }
            vo.setResult("删除权限成功");
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }
}
