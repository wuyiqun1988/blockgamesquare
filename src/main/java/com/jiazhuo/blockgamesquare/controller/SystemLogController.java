package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.qo.SystemLogQueryObject;
import com.jiazhuo.blockgamesquare.service.ISystemlogService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemLogController {
    @Autowired
    private ISystemlogService systemlogService;

    /**
     * 后台操作日志列表
     * @param qo
     * @return
     */
    @RequestMapping(value = "/mgrsite/systemlogList", method = RequestMethod.GET)
    @ResponseBody
    @RequiredPermission("后台操作日志列表")
    public JSONResultVo systemLogPage(@ModelAttribute("qo") SystemLogQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        PageResult result = systemlogService.systemLogPage(qo);
        vo.setResult(result);
        return vo;
    }
}
