package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.service.IUserStatusService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserStatusController {

    @Autowired
    private IUserStatusService userStatusService;

    /**
     * 操作用户:  禁用/恢复
     * @param UID
     * @param state
     * @return
     */
    @RequestMapping(value = "/mgrsite/users/changeStatus", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("操作用户 禁用/恢复")
    public JSONResultVo changeStatus(String UID, Byte state){
        if (StringUtil.isNull(UID)){
            return JSONResultVo.error("UID不能为空");
        }
        if (StringUtil.isNull(state)){
            return JSONResultVo.error("state不能为空");
        }
        JSONResultVo vo = new JSONResultVo();
        boolean ret = userStatusService.changeStatus(UID, state);
        if (!ret){
            return JSONResultVo.error("请选择需要操作的用户");
        }
        if (state == 0){
            vo.setResult("恢复用户成功");
        } else if (state == 1){
            vo.setResult("禁用用户成功");
        } else {
            vo.setResult("请选择禁用或恢复操作");
        }
        return vo;
    }
}
