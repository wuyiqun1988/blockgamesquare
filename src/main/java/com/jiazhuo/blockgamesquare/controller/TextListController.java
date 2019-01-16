package com.jiazhuo.blockgamesquare.controller;

import com.jiazhuo.blockgamesquare.domain.TextList;
import com.jiazhuo.blockgamesquare.qo.PageResult;
import com.jiazhuo.blockgamesquare.service.ITextListService;
import com.jiazhuo.blockgamesquare.util.RequiredPermission;
import com.jiazhuo.blockgamesquare.util.StringUtil;
import com.jiazhuo.blockgamesquare.util.SuperResult;
import com.jiazhuo.blockgamesquare.vo.JSONResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TextListController {
    @Autowired
    private ITextListService textListService;

    /**
     * 文本列表
     * @return
     */
    @RequestMapping(value = "/mgrsite/textList", method = RequestMethod.GET)
    @ResponseBody
    public JSONResultVo textList(){
        JSONResultVo vo = new JSONResultVo();
        List<TextList> data = textListService.queryList();
        vo.setResult(data);
        return vo;
    }

    /**
     * 更新或保存文本
     * @param textList
     * @return
     */
    @RequestMapping(value = "/mgrsite/text/saveOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    @RequiredPermission("更新或保存文本")
    public JSONResultVo saveOrUpdate(TextList textList){
        if (textList.getTid() != null) {
            textListService.update(textList);
            return JSONResultVo.ok("更新成功");
        } else {
            textListService.save(textList);
            return JSONResultVo.ok("保存成功");
        }
    }

    /**
     * 删除文本
     * @param tid
     * @return
     */
    @RequestMapping(value = "/mgrsite/text/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiredPermission("删除文本")
    public JSONResultVo delete(Long tid){
        if (tid != null) {
            textListService.delete(tid);
            return JSONResultVo.ok("删除成功");
        } else {
            return JSONResultVo.error("请选择需要删除的文本");
        }
    }

    /**
     * 获取文本信息(app端)
     * @param title
     * @param type
     * @return
     */
    @RequestMapping(value = "/appsite/textList", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult appText(String title, Byte type){
        TextList result = textListService.queryText(title, type);
        SuperResult res = SuperResult.ok(result);
        if (result == null){
            return SuperResult.ok("无数据");
        }
        res.setMsg(type == 0 ? "获取常见问题成功" : "获取文本信息成功");
        return res;
    }
}
