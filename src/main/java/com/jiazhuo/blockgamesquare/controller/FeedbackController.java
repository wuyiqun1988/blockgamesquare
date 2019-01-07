package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.exception.DisplayableException;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.qo.UserQueryObject;
import com.jiazhuo.blockgamesquare.service.IFeedbackService;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 用户反馈列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/userFeedbackList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo feedbackPage(@ModelAttribute("qo") UserQueryObject qo){
        JSONResultVo vo = new JSONResultVo();
        try {
            PageResult result = feedbackService.feedbackPage(qo);
            vo.setResult(result);
        } catch (DisplayableException e){
            e.printStackTrace();
            vo.setErrorMsg(e.getMessage());
        }
        return vo;
    }

    /**
     * 用户进行反馈
     * @return
     */
    @RequestMapping(value = "/appsite/userFeedback", method = RequestMethod.POST)
    @ResponseBody
    public SuperResult userFeedback(String UID, String content, String contact){
        try {
            feedbackService.userFeedback(UID, content, contact);
            return new SuperResult(0, 0, "反馈成功", null);
        } catch (Exception e){
            e.printStackTrace();
            return new SuperResult(0, 1, "系统异常,反馈失败", null);
        }
    }
}
