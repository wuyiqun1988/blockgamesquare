package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.qo.GameQueryObject;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.QueryObject;
import com.jiazhuo.blockgamesquare.service.INotificationService;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    /**
     * 消息通知列表(app端)
     * @return
     */
    @RequestMapping(value = "/appsite/notifications", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult notifications(@ModelAttribute("qo") QueryObject qo, String UID){
        PageResult result = notificationService.notifications(qo, UID);
        SuperResult res = SuperResult.ok(result);
        res.setMsg("请求消息列表成功");
        return res;
    }
}
